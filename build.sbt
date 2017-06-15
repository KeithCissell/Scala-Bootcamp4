name := "specs2-tutorial"

resolvers += "bintray-banno-oss-releases" at "http://dl.bintray.com/banno/oss"

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-jackson" % "3.5.2",
  "org.asynchttpclient" % "async-http-client" % "2.0.32",
  "org.slf4j" % "slf4j-simple" % "1.7.21" % "runtime",
  "org.specs2" %% "specs2-core" % "3.7.2" % "test"
)
