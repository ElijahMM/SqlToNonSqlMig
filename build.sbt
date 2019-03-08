name := "SqlToNonSqlMigration"

version := "1.0"

lazy val `sqltononsqlmigration` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(ehcache, ws, specs2 % Test, guice,
  "org.postgresql" % "postgresql" % "9.4-1200-jdbc41",
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.6.0",
  "com.typesafe.play" %% "play-slick" % "3.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0",
  "com.h2database" % "h2" % "1.4.197"
)

//conflict between ch.qos.logback and org.slf4j -- multiple bindings--
libraryDependencies ~= {
  _.map(_.exclude("org.slf4j", "slf4j-simple"))
}

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")
