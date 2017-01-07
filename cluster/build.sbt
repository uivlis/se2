import NativePackagerHelper._

val akkaVersion = "2.4.16"

lazy val commonSettings = Seq(
	organization := "com.example",
	version := "0.0.0",
	scalaVersion := "2.11.8",
	libraryDependencies ++= Seq(
		"org.scala-lang" % "scala-compiler" % "2.11.8",
		"org.scala-lang" % "scala-library" % "2.11.8",
		"org.scala-lang" % "scala-reflect" % "2.11.8",
		"org.scala-lang.modules" % "scala-parser-combinators_2.11" % "1.0.4",
		"org.scala-lang.modules" % "scala-xml_2.11" % "1.0.4",
		"com.typesafe.akka" %% "akka-actor" % akkaVersion,
		"com.typesafe.akka" %% "akka-remote" % akkaVersion,
		"com.typesafe.akka" %% "akka-cluster" % akkaVersion,
		"com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion,
		"com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
		"com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion,
		"org.scalatest" %% "scalatest" % "latest.integration" % "test",
		"io.kamon" % "sigar-loader" % "1.6.6-rev002",
		"com.typesafe.slick" %% "slick" % "3.2.0-M2",
		"org.slf4j" % "slf4j-nop" % "1.6.4",
		"com.typesafe.slick" %% "slick-hikaricp" % "3.2.0-M2",
		"com.typesafe.slick" %% "slick-codegen" % "3.2.0-M2",
		"org.slf4j" % "slf4j-nop" % "1.6.4",
		"mysql" % "mysql-connector-java" % "latest.release",
		"org.scalaz" %% "scalaz-core" % "7.2.8",
		"org.scalaz" %% "scalaz-effect" % "7.2.8",
		"org.scalaz" %% "scalaz-concurrent" % "7.2.8"
	),
	javaOptions in run ++= Seq(
      "-Xms128m", "-Djava.library.path=./target/native",
	  "-Xmx1536M", "-Xss1M", "-XX:+CMSClassUnloadingEnabled", "-XX:MaxPermSize=256m"
	  ),
    Keys.fork in run := true,
	mainClass in Compile := Some("Main"),
	//scalacOptions += "-target:jvm-1.7",
	scalacOptions ++= Seq("-unchecked", "-deprecation"),
	licenses := Seq(
		("CC0", url("http://creativecommons.org/publicdomain/zero/1.0"))
	)
)

lazy val goSlick = taskKey[Seq[File]]("gen-tables")

//lazy val slickCodeGenTask

lazy val root = (project in file(".")).
	settings(commonSettings: _*).
	settings(
		name := baseDirectory.value.getName,
		(unmanagedSourceDirectories in Compile) += file((sourceManaged.value / "slick").getPath),
		goSlick := {
					val outputDir = (sourceManaged.value / "slick").getPath // place generated files in sbt's managed sources folder
					val url = "jdbc:mysql://localhost:3306/se?profileSQL=true&nullNamePatternMatchesAll=true" 
					val jdbcDriver = "com.mysql.cj.jdbc.Driver"
					val slickDriver = "slick.jdbc.MySQLProfile"
					val pkg = "demo"
					val user = "root"
					val password = "Rappjak0tilc0pp"
					toError((runner in Compile).value.run("slick.codegen.SourceCodeGenerator", (dependencyClasspath in Compile).value.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg, user, password), streams.value.log))
					val fname = outputDir + "/demo/Tables.scala"
					Seq(file(fname))
				} // register manual sbt command
		//sourceGenerators in Compile += goSlick // register automatic code generation on every compile, remove for only manual use
	).enablePlugins(JavaServerAppPackaging)
	//.aggregate(util, core)

/*
lazy val core = (project in file("core")).
	settings(commonSettings: _*).
	settings(
		name := baseDirectory.value.getName
	).enablePlugins(JavaServerAppPackaging)

lazy val util = (project in file("util")).
	settings(commonSettings: _*).
	settings(
		name := baseDirectory.value.getName
	).enablePlugins(JavaServerAppPackaging)
*/

fork in run := true

//javaHome := Some(file("C:/Program Files/Java/jre7"))