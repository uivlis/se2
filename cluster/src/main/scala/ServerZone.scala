import language.postfixOps
import scala.concurrent.duration._
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.PoisonPill
import akka.actor.Props
import akka.actor.RootActorPath
import akka.cluster.Cluster
import akka.cluster.ClusterEvent.CurrentClusterState
import akka.cluster.ClusterEvent.MemberUp
import akka.cluster.Member
import akka.cluster.MemberStatus
import com.typesafe.config.ConfigFactory
import akka.actor.Terminated
import akka.routing.{RoundRobinRoutingLogic, ActorRefRoutee, Router}
import java.util.Calendar
import scala.io.StdIn.readLine
import Command._
import scala.concurrent.Await
import akka.pattern.ask
import scala.concurrent.duration._
import akka.util.Timeout
import java.util.concurrent.TimeUnit
import scalaz._, Scalaz._
import scalaz.std._

class ServerZone extends Actor{

	val cluster = Cluster(context.system)
	
	var router = {
		val routees = Vector.fill(3) {
			ActorRefRoutee(context.actorOf(Props[ServerZoneCore]))
		}
		Router(RoundRobinRoutingLogic(), routees) 
	}
	
	var master: Option[ActorRef] = None 
	
	override def preStart(): Unit = cluster.subscribe(self, classOf[MemberUp])
	override def postStop(): Unit = cluster.unsubscribe(self)
	
	var camerakkks = IndexedSeq.empty[ActorRef]
	var cameraZones = IndexedSeq.empty[Circle]
	var lastCarsSeen = "0"
	var zone = "0"
	var zoneCovered: Option[Circle] = None
	var serverZoneHelpers = IndexedSeq.empty[ActorRef]
	
	def receive = {
		case k: String => zone = k
		
		case Job(locations, command, demander, processor) if command == GetZoneCovered => 
			for (camera <- camerakkks) {
				val r = Await.result(ask(camera, Job(locations, command, demander, self))(Timeout(3, TimeUnit.SECONDS)), Duration.Inf).asInstanceOf[ResultZone]
				cameraZones +:= r.zoneCovered
			}
			zoneCovered = Some(computeZoneCovered())
			demander ! ResultZone(Job(locations, command, demander, processor), zoneCovered.get)

		case img: ImageTraffic => router.route(img, self)
		
		case analImg: AnalImgTraffic if (master != None)=> 
			master.get ! analImg
			for (s <- serverZoneHelpers) {s ! analImg}
			
		case analImg: AnalImgTraffic => sender() ! "No master up!"
		
		case state: CurrentClusterState =>
			state.members.filter(_.status == MemberStatus.Up) foreach register
		
		case MemberUp(m) => register(m)
		
		case CameraRegistration if !camerakkks.contains(sender())  => 
			context watch sender()
			camerakkks = camerakkks :+ sender()
			sender() ! ZonekRegistration
		
		case MasterRegistration if (master == None) =>
			context watch sender()
			master = Some(sender())
		
		case ServerHelperImgRegistration => 
			context watch sender()
			serverZoneHelpers +:= sender()
		
		case Terminated(a) => camerakkks.filterNot(_ == a)
			if (master.get == a) master = None
			serverZoneHelpers.filterNot(_ == a)
		
		case _ => println("What's this?")
	}
	
	def register(member: Member): Unit = {
	/*	if (member.hasRole("zone" + zone))
		context.actorSelection(RootActorPath(member.address) / "user" / member.getRoles.iterator.next) ! PoisonPill
	*/	if (member.hasRole("master") && (master == None)){
			context.actorSelection(RootActorPath(member.address) / "user" / "master") ! ZonekRegistration
		}
	}
	
	def computeZoneCovered(): Circle = {
		cameraZones reduceLeft ((c1, c2) => computeZoneCovering(c1, c2))
	}
	
	def computeZoneCovering(c1:Circle, c2:Circle): Circle = {
		
		val absp: (Double, Double) => Double = {
			case (x, y) =>
				scala.math.sqrt(x * x + y * y)
		}
		
		var m = (c2.center.y - c1.center.y) / (c2.center.x - c1.center.x)
		var cos2 = 1 / (1 + m * m)
		var xp = c1.radius * scala.math.sqrt(cos2) + c1.center.x
		var xm = - c1.radius * scala.math.sqrt(cos2) + c1.center.x
		var yp = c1.radius * scala.math.sqrt(1 - cos2) + c1.center.y
		var ym = - c1.radius * scala.math.sqrt(1 - cos2) + c1.center.y
		var r1 = absp(c1.center.x - xp, c1.center.y - yp)
		var r2 = absp(c1.center.x - xm, c1.center.y - ym)
		if (r1 > r2) Circle(Point(xp, yp), r1) 
			else Circle(Point(xm, ym), r2)
	}
	
}

object ServerZone {
	def main(args: Array[String]) {
		val (port, k) = if (args.isEmpty) ("0", "0")
			else if (args.length == 1) (args(0), "0")
			else (args(0), args(1))
		val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port").
			withFallback(ConfigFactory.parseString(s"akka.cluster.roles = [zone${k}]")).
			withFallback(ConfigFactory.load())
		val system = ActorSystem("ClusterSystem", config)
		val zonek = system.actorOf(Props[ServerZone], name = "zone" + k)
		zonek ! k
		var text = readLine
		while(text != "exit"){
			text = readLine
		}
		zonek ! PoisonPill
	}
}

class ServerZoneCore extends Actor {

	def computeStateOfRoad (stateOfRoad: Double) : String = {
		if (stateOfRoad < 3) "foarte proasta"
		else if (stateOfRoad < 6) "proasta"
		else if (stateOfRoad < 9) "buna"
		else "foarte buna"
	}
	def computeCondMeteo (condMeteo: ConditionMeteo): String = {
		if (condMeteo.poley == "y" ) "Poley"
		else if (condMeteo.snow == "y") "Snowy"
		else if (condMeteo.precip > 50) "Rainny"
		else if (condMeteo.precip > 20) "Cloudy"
		else if (condMeteo.temp > 30) "Sunny"
		else if (condMeteo.temp > 20) "Lovely"
		else "Freezy"
	}

	def receive = {
		case ImageTraffic(street, noOfCars, speed, condMeteo, stateOfRoad, camera, time)
		=>
			var stringStateOfRoad = computeStateOfRoad(stateOfRoad)
			var stringCondMeteo = computeCondMeteo(condMeteo)
			sender() ! AnalImgTraffic(street, noOfCars, speed, stringCondMeteo, stringStateOfRoad, camera, time)
		case noMaster: String => println("Camera, we have no master.")
		case _ => println("Wha?")
	}
}