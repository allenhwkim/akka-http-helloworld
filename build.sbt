enablePlugins(JavaAppPackaging)

name := "akka-http-hello-world"
organization := "helloworld"
version := "0.1"
scalaVersion := "2.11.8"
scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")
mainClass in Compile := Some("HelloWorld")

libraryDependencies ++= {
  val akkaStreamVersion = "2.5.4"
  val akkaHttpVersion = "10.0.10"
  val scalaTestV = "3.0.4"

  Seq(
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-jackson" % akkaHttpVersion,

    // Only when running against Akka 2.5 explicitly depend on akka-streams in same version as akka-actor
    "com.typesafe.akka" %% "akka-stream" % akkaStreamVersion, // or whatever the latest version is
    "com.typesafe.akka" %% "akka-actor"  % akkaStreamVersion
  )
}

