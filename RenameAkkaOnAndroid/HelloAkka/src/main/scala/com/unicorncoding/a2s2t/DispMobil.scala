package com.unicorncoding.a2s2t

import language.postfixOps
import scala.concurrent.duration._
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.PoisonPill
import akka.actor.Props
import akka.actor.Terminated
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import scala.io.StdIn.readLine
import Command._

class DispMobil extends Actor {

	var backends = IndexedSeq.empty[ActorRef]
	var jobCounter = 0
	
	def receive = {
		case job: Job if backends.isEmpty =>
			sender() ! JobFailed("Service unavailable, try again later", job)
			
		case job: Job =>
			jobCounter += 1
			backends(jobCounter % backends.size) ! job
			
		case JobFailed(text, job) => println(text)	
			
		case BackendRegistration if !backends.contains(sender()) =>
			context watch sender()
			backends = backends :+ sender()
			
		case Terminated(a) =>
			backends = backends.filterNot(_ == a)
			
		case r: ResultPath =>
			println("The result is: " + r.path._2)
			
		case r: ResultImg =>
			println("It's " + r.img.condMeteo)
		
		case r: ResultCong =>
			println("It's map with " + r.wc.map)
		
		case _ => println("What's this?")
	}

}

object DispMobil {

	def main(args: Array[String]): Unit = {
		val port = if (args.isEmpty) "0"
			else args(0)
		val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port").
			withFallback(ConfigFactory.parseString("akka.cluster.roles = [frontend]")).
			withFallback(ConfigFactory.load())
		
		val system = ActorSystem("ClusterSystem", config)
		val frontend =  system.actorOf(Props[DispMobil], name = "frontend")
		
		Thread.sleep(10000)
		
		println("Hi there! Where are we? Tell me your Latitude: (degrees). Or do you want to exit? ")
		var currGpsLat = readLine
		while(currGpsLat != "exit"){
			println("Ok, your Longitude now: (degrees)")
			var currGpsLong = readLine
			println("Well, what would you like? : (path, realtime, harta) ")
			var command = readLine
			while(!(Set("path","realtime","harta") contains command)){
				println("Not good. Please enter: (path, realtime, harta)")
				command = readLine
			}
			command match {
				case "path" => 
					println("Choose dest Lat: (deg)")
					var destGpsLat = readLine
					println("Choose dest Long: (deg)")
					var destGpsLong = readLine
					frontend ! Job(Locations(Gps(currGpsLat.toDouble, currGpsLong.toDouble), Gps(destGpsLat.toDouble, destGpsLong.toDouble)), FindPath, frontend, frontend)
				case "realtime" => 
					println("Choose dest Lat: (deg)")
					var destGpsLat = readLine
					println("Choose dest Long: (deg)")
					var destGpsLong = readLine
					frontend ! Job(Locations(Gps(currGpsLat.toDouble, currGpsLong.toDouble), Gps(destGpsLat.toDouble, destGpsLong.toDouble)), GetRealTimeTraffic, frontend, frontend)
				case "harta" => 
					frontend ! Job(Locations(Gps(currGpsLat.toDouble, currGpsLong.toDouble), Gps(0, 0)), GetHartaCong, frontend, frontend)
				}
			println("Again! Where are we? Tell me your Latitude: (degrees). Or do you want to exit? ")
			currGpsLat = readLine
		}
		frontend ! PoisonPill
		
	}

}
