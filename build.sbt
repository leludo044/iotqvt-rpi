name := """iotqvt-rpi"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs
)

libraryDependencies += "javax.websocket" % "javax.websocket-api" % "1.1"

libraryDependencies += "org.glassfish.tyrus" % "tyrus-server" % "1.10"

libraryDependencies += "org.glassfish.tyrus" % "tyrus-container-grizzly-server" % "1.10"
