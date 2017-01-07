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
import java.util.Calendar
import Command._
class Camera extends Actor {

	var zonek : Option[ActorRef] = None
	val cluster = Cluster(context.system)
	override def preStart(): Unit = cluster.subscribe(self, classOf[MemberUp])
	override def postStop(): Unit = cluster.unsubscribe(self)
	var camk = "0"
	var camkk = "0"
	var circle: Option[Circle] = None
	def receive = {
		case kkk: String => if (kkk.contains("kk")) camkk = kkk.substring(2)
							else if (kkk.contains("k")) camk = kkk.substring(1)
		
		case c: Circle => circle = Some(c)
		
		case j: Job if j.command == GetZoneCovered => 
			j.processor ! ResultZone(j, circle.get)
 		
		case ImageTraffic   if (zonek == None) =>
			println("Fail. No ServerZone up.")
			
		case img: ImageTraffic =>
			zonek.get ! img
		
		case ZonekRegistration if (zonek == None) =>
			context watch sender()
			zonek = Some(sender())
		
		case state: CurrentClusterState =>
			state.members.filter(_.status == MemberStatus.Up) foreach register
		case MemberUp(m) => register(m)
		
		case Terminated(a) =>
			zonek = None
				
		case _ => println("What's this?")
	}
	
	def register(member: Member): Unit = {
	/*	if (member.hasRole("camera" + camk + "_" + camkk))
			context.actorSelection(RootActorPath(member.address) / "user" / member.getRoles.iterator.next) ! PoisonPill
	*/	if (member.hasRole("zone" + camk))
			context.actorSelection(RootActorPath(member.address) / "user" / ("zone" + camk)) ! CameraRegistration
	}

}

object Camera {

	def main(args: Array[String]): Unit = {
		val (port, k, kk) = if (args.isEmpty) ("0", "0", "0")
			else if (args.length == 1) (args(0), "0", "0")
			else if (args.length == 2) (args(0), args(1), "0")
			else (args(0), args(1), args(2))
		val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port").
			withFallback(ConfigFactory.parseString(s"akka.cluster.roles = [camera${k}_${kk}]")).
			withFallback(ConfigFactory.load())
		val system = ActorSystem("ClusterSystem", config)
		val camerakkk =  system.actorOf(Props[Camera], name = "camera" + k + "_" + kk)
		camerakkk ! ("k" + k)
		camerakkk ! ("kk" + kk)
		Thread.sleep(10000)
		println("Which street this camera services?")
		var name = readLine
		println("Which city are we in?")
		var city = readLine
		println("Which Country?")
		var country = readLine
		println("How many lanes?")
		var noOfLanes = readLine.toInt
		println("Let me know the street GPS coord: Start Lat: (in degrees) ")
		var startLat = readLine.toDouble
		println("Start Long: (also, in degrees)")
		var startLong = readLine.toDouble
		println("Stop Lat: (deg)")
		var stopLat = readLine.toDouble
		println("Stop Long:(deg)")
		var stopLong = readLine.toDouble
		var street = Street(name, city, country, noOfLanes, startLat, startLong, stopLat, stopLong)
		println("How many cars do I see? Or do you want to exit?: ")
		var text = readLine
		while(text != "exit"){
			var noOfCars = text.toInt
			while(noOfCars < 0){
				println("No sir. Another: ")
				noOfCars = readLine.toInt
			}
			println("How about the speed?: (km/h)")
			var speed = readLine.toDouble
			while(speed < 0 || speed > 300){
				println("Impossible. Have another: ")
				speed = readLine.toDouble
			}
			println("Now, meteo: temp (Celsius): ")
			var temp = readLine.toDouble
			while(temp < -273.15 || temp > 100){
				println("That's a hack of a temp. Another: ")
				temp = readLine.toDouble
			}
			println("Precipiations? (chances in %):")
			var precip = readLine.toDouble
			while(precip > 100 || precip < 0){
				println("Nope, another:")
				precip = readLine.toDouble
			}
			println("Any snow? (y/n):")
			var snow = readLine
			while(snow != "y" && snow != "n"){
				println("y/n please: ")
				snow = readLine
			}
			println("Any poley?: (y/n)")
			var poley = readLine
			while(poley != "y" && poley != "n"){
				println("please do y/n: ")
				poley = readLine
			}
			println("Yeah, how's the road? (1 to 10):")
			var stateOfRoad = readLine.toDouble
			while(stateOfRoad < 0 || stateOfRoad > 10){
				println("Please do 1 to 10: ")
				stateOfRoad = readLine.toDouble
			}
			camerakkk ! Circle(Point((street.startLat + street.stopLat)/2, (street.startLong + street.stopLong)/2 ), scala.math.sqrt(scala.math.pow(street.startLat - street.stopLat , 2) + scala.math.pow(street.startLong - street.stopLong , 2)))
			camerakkk ! ImageTraffic(street, noOfCars, speed, ConditionMeteo(temp, precip, snow, poley), stateOfRoad, camerakkk, Calendar.getInstance)
			println("Ok, thanks. Here we go again: ")
			println("How many cars do I see? Or do you want to exit?: ")
			text = readLine
		}
		camerakkk ! PoisonPill
		
	}
}
