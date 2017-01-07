import language.postfixOps
import scala.concurrent.duration._
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.PoisonPill
import akka.actor.RootActorPath
import akka.cluster.Cluster
import akka.cluster.ClusterEvent.CurrentClusterState
import akka.cluster.ClusterEvent.MemberUp
import akka.cluster.Member
import akka.cluster.MemberStatus
import com.typesafe.config.ConfigFactory
import scala.io.StdIn.readLine
import akka.actor.Terminated
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.MySQLProfile.backend.DatabaseDef
import slick.backend.DatabaseConfig
import scala.concurrent.ExecutionContext.Implicits.global
import demo.Tables
import Tables._
import slick.jdbc.JdbcBackend
import scala.util.{Success, Failure}
import java.util.NoSuchElementException
import scala.concurrent.Await
import java.util.Calendar
import java.sql.Timestamp
import Command._
import scala.concurrent.Await
import akka.pattern.ask
import scala.concurrent.duration._
import akka.util.Timeout
import java.util.concurrent.TimeUnit
import scalaz._, Scalaz._
import scala.util.control.Breaks

class Server extends Actor {

	val cluster = Cluster(context.system)
	val serverDB = context.actorOf(Props[ServerDB], name = "serverDB")
	
	override def preStart(): Unit = cluster.subscribe(self, classOf[MemberUp])
	override def postStop(): Unit = cluster.unsubscribe(self)
	var zoneks = IndexedSeq.empty[ActorRef]
	var jobCounter = 0;
	
	var wcosts = World(Map[Gps, List[(Double, Gps)]]())
	var serverZoneCovering: Option[ActorRef] = None
	var serverHelpersImg = IndexedSeq.empty[ActorRef]
	var serverHelpersCong = IndexedSeq.empty[ActorRef]
	var imgDemanders = IndexedSeq.empty[ActorRef]
	var congDemanders = IndexedSeq.empty[ActorRef]
	val absp: (Double, Double) => Double = {
			case (x, y) =>
				scala.math.sqrt(x * x + y * y)
		}
	
	val insideZone: (Gps, Circle) => Boolean = {
		case (gps, c) =>
			absp(gps.lat - c.center.x, gps.long - c.center.y) < c.radius
	}
	
	def receive = {
		case Job(locations, command, demander, processor) => 
			for(i <- 0 to imgDemanders.size - 1){
				if (imgDemanders(i) == sender()) {
					imgDemanders.patch(i, Nil, 1)
					serverHelpersImg.patch(i, Nil, 1)
				}
			}
			for(i <- 0 to congDemanders.size - 1){
				if (congDemanders(i) == sender()) {
					congDemanders.patch(i, Nil, 1)
					serverHelpersCong.patch(i, Nil, 1)
				}
			}
			command match {
				case FindPath => 
					serverDB ! Job(locations, command, demander, processor)
				case GetRealTimeTraffic => 
					for (
						szone <- zoneks
					){
						var r = Await.result(ask(szone, Job(locations, command, self, processor))(Timeout(3, TimeUnit.SECONDS)), Duration.Inf).asInstanceOf[ResultZone]
						if (insideZone(locations.destGps, r.zoneCovered)) serverZoneCovering = Some(szone)
					}
					var h = context.actorOf(Props[ServerHelperImg])
					h ! serverDB
					serverHelpersImg +:= h
					h ! (serverZoneCovering, Job(locations, command, demander, processor))					
					imgDemanders +:= sender()
				case GetHartaCong => 
					var h = context.actorOf(Props[ServerHelperImg])
					h ! serverDB
					serverHelpersImg +:= h
					h ! (serverZoneCovering, Job(locations, command, demander, processor))					
					imgDemanders +:= sender()
			}
		case r: ResultWorld => 
			wcosts = r.wc
			r.job.processor ! r
		//case => to continue
			
		case analImg: AnalImgTraffic => serverDB ! analImg
		case state: CurrentClusterState =>
			state.members.filter(_.status == MemberStatus.Up) foreach register
		case MemberUp(m) => register(m)
		case ZonekRegistration if !zoneks.contains(sender()) =>
			context watch sender()
			zoneks = zoneks :+ sender()
			sender() ! MasterRegistration
			println("curr size is: " + zoneks.size)
		case Terminated(a) => zoneks = zoneks.filterNot(_ == a)
		case _ => println("What's this?")
	}
	
	def register(member: Member) {
		if (member.hasRole("backend"))
			context.actorSelection(RootActorPath(member.address) / "user" / "backend") ! MasterRegistration			
	}
}

object Server {
	def main(args:Array[String]):Unit = {
		val port = if (args.isEmpty) "0" else args(0)
		val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port").
			withFallback(ConfigFactory.parseString("akka.cluster.roles = [master]")).
			withFallback(ConfigFactory.load())
		
		val system = ActorSystem("ClusterSystem", config)
		val master = system.actorOf(Props[Server], name = "master")
		var text = readLine
		while(text != "exit"){
			text = readLine
		}
		master ! PoisonPill
	}
}

class ServerDB extends Actor {
	
	var db: Option[DatabaseDef] = None 
	
	override def preStart(): Unit = {db = Some(Database.forURL("jdbc:mysql://localhost:3306/se?profileSQL=true&nullNamePatternMatchesAll=true", "root", "Rappjak0tilc0pp"))}
	override def postStop(): Unit = {db.get.close}
	
	def computeWorld(): World = {
		try{
			var q = for {
				c1 <- Cotitures
				c2 <- Cotitures if c1.uniqueCotitureId =!= c2.uniqueCotitureId
				i1 <- Intersections
				i2 <- Intersections if (i1.id =!= i2.id && (
															((i1.idStrada1 ===  i2.idStrada1) && (i1.idStrada1 =!= 0)) || 
															((i1.idStrada1 === i2.idStrada2) && (i1.idStrada1 =!= 0))||
															((i1.idStrada2 === i2.idStrada1) && (i1.idStrada2 =!= 0))|| 
															((i1.idStrada2 === i2.idStrada2) && (i1.idStrada2 =!= 0)))
														&& (i1.uniqueIntersectionId === c1.uniqueCotitureId)
														&& (i2.uniqueIntersectionId === c2.uniqueCotitureId) ) 
			} yield ((c1.latitudineGps, c1.longitudineGps), (c2.latitudineGps, c2.longitudineGps)) 
			var r1 = Await.result(db.get.run(q.result), Duration.Inf)
			var r2 = r1.map {
				case ((klat, klong), (vlat, vlong)) => Locations(Gps(klat, klong), Gps(vlat, vlong))
			}
			var m: World = World(Map[Gps, List[(Double, Gps)]]())
			for (
				Locations(lc, ld) <- r2
			) {
				var l1 = m.map.getOrElse(lc, List()).+:((0.0, ld))
				var l2 = m.map.getOrElse(ld, List()).+:((0.0, lc))
				m = World(m.map + (lc -> l1))
				m = World(m.map + (ld -> l2))
			}
			m
		}finally ()	
	}
	
	def computeCosts(w: World): World = {
		try{
			def computeCostCotitures(c1: Int, c2: Int): Double = {
				try {
					var q1 = for {
						i1 <- Intersections
						i2 <- Intersections if (i1.id =!= i2.id && (i1.idStrada1 === i2.idStrada1 || i1.idStrada1 === i2.idStrada2)
																&& (i1.idStrada1 =!= 0)
																&& (i1.uniqueIntersectionId === c1)
																&& (i2.uniqueIntersectionId === c2))
					} yield i1.idStrada1
					var r1 = Await.result(db.get.run(q1.result), Duration.Inf)
					var q2 = for {
						i1 <- Intersections
						i2 <- Intersections if (i1.id =!= i2.id && (i1.idStrada2 === i2.idStrada1 || i1.idStrada2 === i2.idStrada2 )//TO DO HERE
																&& (i1.idStrada2 =!= 0)
																&& (i1.uniqueIntersectionId === c1)
																&& (i2.uniqueIntersectionId === c2))
					} yield i1.idStrada2
					var r2 = Await.result(db.get.run(q2.result), Duration.Inf)
					var r = r1 ++ r2
					var sId = r.iterator.next
					var p = computePerioadaZi (Calendar.getInstance)
					var pId = Await.result(db.get.run((for {
										p1 <- PerioadaZi if p1.descriere === p
									} yield p1.id).result),
								Duration.Inf
							).iterator.next
					var q3 = for {
						d <- DateTraffic if ((d.idStrada === sId) && (d.idPerioadaZi === pId))
					} yield (d.idCondMeteo, d.nrMediuAutovehicule, d.vitezaMedieAutovehicule, d.idStareaDrumului)
					var data = Await.result(db.get.run(q3.result), Duration.Inf).iterator
					var cost = 0
					while(data.hasNext){
						var d = data.next
						d._1 match {
							case 1 => cost += 5 //Rainny
							case 2 => cost += 1 //Sunny
							case 3 => cost += 2 //Cloudy
							case 4 => cost += 8 //Snowy
							case 6 => //Lovely
							case 7 => cost += 10 //Poley
							case 8 => cost += 6 //Freezy
						}
						cost += d._2.toInt
						cost += 60 / d._3.toInt
						d._4 match {
							case 1 => //foarte buna
							case 2 => cost += 1 //buna
							case 3 => cost += 3 //proasta
							case 4 => cost += 5 //foarte proasta
						}
					}
					cost
				} finally ()
			}
			var m1 = w.map
			var m2 = Map[Gps, List[(Double, Gps)]]()
			for (
				(gps1, l1) <- m1
			) {
				var c1gps = Await.result(db.get.run((for { 
							c1 <- Cotitures if (c1.latitudineGps === gps1.lat && c1.longitudineGps === gps1.long)
						} yield c1.uniqueCotitureId).result
					),
					Duration.Inf
				).iterator.next
				for (
					(d1, gps2) <- l1
				) {
					var c2gpsIt = Await.result(db.get.run((for { 
								c2 <- Cotitures if (c2.latitudineGps === gps2.lat && c2.longitudineGps === gps2.long)
							} yield c2.uniqueCotitureId).result
						),
						Duration.Inf
					).iterator
					while (c2gpsIt.hasNext) {
						var c2gps = c2gpsIt.next
						var d2 = computeCostCotitures(c1gps, c2gps)
						var l2 = m2.getOrElse(gps1, List()).+:((d2, gps2))
						m2 = m2 + (gps1 -> l2)
					}
				}
			}
			World(m2)
		}finally ()
	}
	
	def computePerioadaZi (time: Calendar): String = {
		var stringedMinute = 
			if (time.get(Calendar.MINUTE) < 15) "00"
			else if (time.get(Calendar.MINUTE) < 30) "15"
			else if (time.get(Calendar.MINUTE) < 45) "30"
			else if (time.get(Calendar.MINUTE) < 60) "45"
		time.get(Calendar.HOUR_OF_DAY) + ":" + stringedMinute
	}
	
	def receive = {
	
		case analImg: AnalImgTraffic => 
			try{
				var streetId = 0
				var q = for {
					s <- Strazi if s.strada === analImg.street.name
				} yield s.id
				var ids = Await.result(db.get.run(q.result), Duration.Inf)
				streetId = if (!ids.isEmpty) ids.iterator.next
						else Await.result(
							db.get.run(
								(Strazi returning Strazi.map(_.id)) += StraziRow(
									0,
									analImg.street.city,
									analImg.street.name,
									analImg.street.country,
									analImg.street.noOfLanes,
									Some(analImg.street.startLat), 
									Some(analImg.street.startLong),
									Some(analImg.street.stopLat), 
									Some(analImg.street.stopLong)
									)
								),
								Duration.Inf
							)
				q = for {
					c <- CondMeteo if c.descriere === analImg.condMeteo
				} yield c.id
				var condMeteoId = Await.result(db.get.run(q.result), Duration.Inf).iterator.next
				q = for {
					s <- StareaDrumului if s.descriere === analImg.stateOfRoad
				} yield s.id
				var stareaDrumuluiId = Await.result(db.get.run(q.result), Duration.Inf).iterator.next
				q = for {
					p <- PerioadaZi if p.descriere === computePerioadaZi(analImg.time)
				} yield p.id
				var perioadaZiId = Await.result(db.get.run(q.result), Duration.Inf).iterator.next
				var dateTrafficId = Await.result(db.get.run(
						DateTraffic returning DateTraffic.map(_.id) += DateTrafficRow(
								0,
								streetId,
								new Timestamp(analImg.time.getTimeInMillis),
								condMeteoId,
								perioadaZiId,
								analImg.noOfCars,
								analImg.speed,
								stareaDrumuluiId
							)
						), 
						Duration.Inf
					)
			} finally ()
		
		case job: Job =>
			var w = computeWorld()
			var wc = computeCosts(w)
			sender() ! ResultWorld(job, wc)
	}
}

class ServerHelperImg extends Actor{

	var job: Option[Job] = None
	var serverDB: Option[ActorRef] = None
	def receive = {
		case sDB: ActorRef => 
			serverDB = Some(sDB)
		case (s: ActorRef, j: Job) => 
			s ! ServerHelperImgRegistration
			job = Some(j)
		case analImg: AnalImgTraffic =>
			job.get.processor ! ResultImg(job.get, analImg)
	}
}

class ServerHelperCong extends Actor {
	var job: Option[Job] = None
	var serverDB: Option[ActorRef] = None
	def receive = {
		case sDB: ActorRef => 
			serverDB = Some(sDB)
		case (s: ActorRef, j: Job) => 
			s ! ServerHelperImgRegistration
			job = Some(j)
		case analImg: AnalImgTraffic => 
			var rw = Await.result(ask(serverDB.get, job.get)(Timeout(3, TimeUnit.SECONDS)), Duration.Inf).asInstanceOf[ResultWorld]
			job.get.processor ! ResultCong(job.get, rw.wc)
	}
}
