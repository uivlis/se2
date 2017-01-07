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
import scala.io.StdIn.readLine

class ServerApp extends Actor {
	
	val cluster = Cluster(context.system)
	var master: Option[ActorRef] = None 
	override def preStart(): Unit = cluster.subscribe(self, classOf[MemberUp])
	override def postStop(): Unit = cluster.unsubscribe(self)
	
	def receive = {
		case Job(gps, command, demander, processsor) if (master != None) => master.get ! Job(gps, command, demander, self)
		case job: Job => sender() ! JobFailed("No master server, sorry.", job)
		case r: ResultWorld => 
			r.job.demander ! ResultPath(r.job, computeShortestPath(r.wc, r.job.locations)) 
		case r: ResultImg => 
			r.job.demander ! r	
		case r: ResultCong =>
			r.job.demander ! r
		case state: CurrentClusterState => 
			state.members.filter(_.status == MemberStatus.Up) foreach register
		case MemberUp(m) => register(m)
		case MasterRegistration if (master == None) => 
			context watch sender()
			master = Some(sender())
		case Terminated(a) => master = None
		case _ => println("What's this?")
	}
	
	def register(member: Member): Unit = {
		if (member.hasRole("frontend"))
			context.actorSelection(RootActorPath(member.address) / "user" / "frontend") ! 
			BackendRegistration
	}
	
	def computeShortestPath(wc: World, loc: Locations): (Double, List[Gps]) = {
		type Path[Key] = (Double, List[Key])
		println(wc.map)
		def Dijkstra[Key](lookup: Map[Key, List[(Double, Key)]], fringe: List[Path[Key]], dest: Key, visited: Set[Key]): Path[Key] = fringe match {
			case (dist, path) :: fringe_rest =>
				path match {
					case key :: path_rest =>
						if (key == dest) (dist, path.reverse)
						else {
							val paths = lookup(key).flatMap {case (d, key) => if (!visited.contains(key)) List((dist + d, key :: path)) else Nil}
							val sorted_fringe = (paths ++ fringe_rest).sortWith {case ((d1, _), (d2, _)) => d1 < d2}
							Dijkstra(lookup, sorted_fringe, dest, visited + key)
						}
				}
			case Nil => (0, List())
		}
		Dijkstra[Gps](wc.map, List((0.0, List(loc.currGps))), loc.destGps, Set())
 	}
}

object ServerApp {

	def main(args: Array[String]): Unit = {
		val port = if (args.isEmpty) "0"
			else args(0)
		val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port").
			withFallback(ConfigFactory.parseString("akka.cluster.roles = [backend]")).
			withFallback(ConfigFactory.load())
		
		val system = ActorSystem("ClusterSystem", config)
		val backend = system.actorOf(Props[ServerApp], name = "backend")
		var text = readLine
		while(text != "exit"){
			text = readLine
		}
		backend ! PoisonPill
	}

}
