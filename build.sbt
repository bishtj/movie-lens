name := "movie-lens-project"
version := "1.0"
scalaVersion := "2.11.8"

val sparkVersion = "2.4.5"
val catsVersion = "1.1.0"

val scalaTestVersion = "3.0.0"
val sparkTestingBaseVersion = "2.4.5_0.14.0"

libraryDependencies ++= Seq(

  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",

  "org.typelevel" % "cats-core_2.11" % catsVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
  "com.holdenkarau" %% "spark-testing-base" % sparkTestingBaseVersion % "test"
)
// Parallel execution of tests not supported, as all tests should share same spark session
parallelExecution in ThisBuild := false

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

assemblyMergeStrategy in assembly := {
  case PathList("org","aopalliance", xs @ _*) => MergeStrategy.last
  case PathList("javax", "inject", xs @ _*) => MergeStrategy.last
  case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
  case PathList("javax", "activation", xs @ _*) => MergeStrategy.last
  case PathList("org", "apache", xs @ _*) => MergeStrategy.last
  case PathList("com", "google", xs @ _*) => MergeStrategy.last
  case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.last
  case PathList("com", "codahale", xs @ _*) => MergeStrategy.last
  case PathList("com", "yammer", xs @ _*) => MergeStrategy.last
  case "about.html" => MergeStrategy.rename
  case "META-INF/ECLIPSEF.RSA" => MergeStrategy.last
  case "META-INF/mailcap" => MergeStrategy.last
  case "META-INF/mimetypes.default" => MergeStrategy.last
  case "plugin.properties" => MergeStrategy.last
  case "log4j.properties" => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}





