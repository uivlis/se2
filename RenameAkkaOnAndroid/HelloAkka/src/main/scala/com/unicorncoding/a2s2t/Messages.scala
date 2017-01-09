package com.unicorncoding.a2s2t

import akka.actor.ActorRef
import java.util.Calendar

final case object Command extends Enumeration {
	type Command = Value
	val FindPath = Value("Find Path")
	val GetRealTimeTraffic = Value("GetRealTimeTrafficImg")
	val GetHartaCong = Value("GetHartaCong")
	val GetZoneCovered = Value("GetZoneCovered")
}

import Command._

final case class Job(locations: Locations, command: Command, demander: ActorRef, processor: ActorRef)
final case class ResultWorld(job: Job, wc: World)
final case class ResultPath(job: Job, path: (Double, List[Gps]))
final case class ResultZone(job: Job, zoneCovered: Circle)
final case class ResultImg(job: Job, img: AnalImgTraffic)
final case class ResultCong(job: Job, wc: World)
final case class JobFailed(reason: String, job: Job)
final case class ImageTraffic(street: Street, noOfCars: Int, speed: Double, condMeteo: ConditionMeteo, stateOfRoad: Double, camera: ActorRef, time: Calendar)
final case class AnalImgTraffic(street: Street, noOfCars: Int, speed: Double, condMeteo: String, stateOfRoad: String, camera: ActorRef, time: Calendar)
final case class ConditionMeteo(temp: Double, precip: Double, snow: String, poley: String) 
final case class Street(name: String, city: String, country: String, noOfLanes: Int, startLat: Double, startLong: Double, stopLat: Double, stopLong: Double)
final case class Gps(lat: Double, long: Double)
final case class Locations(currGps: Gps, destGps: Gps)
final case class World(map: Map[Gps, List[(Double, Gps)]])
final case class Circle(center: Point, radius: Double)
final case class Point(x: Double, y: Double)
case object BackendRegistration
case object MasterRegistration
case object CameraRegistration
case object ZonekRegistration
case object ServerHelperImgRegistration
