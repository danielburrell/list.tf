name := "wanted"

version := "1.0-SNAPSHOT"

maintainer := "Daniel Burrell <daniel.burrell@gmail.com>"

packageDescription := "Package description"

debianPackageDependencies in Debian ++= Seq("java2-runtime")

debianPackageRecommends in Debian += "git"

lazy val root = (project in file(".")).enablePlugins(PlayJava).enablePlugins(JDebPackaging)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.2.3",
  "org.expressme" % "JOpenId" % "1.08",
  "org.springframework" % "spring-jdbc" % "4.0.7.RELEASE",
  "org.springframework" % "spring-web" % "4.0.7.RELEASE",
  "org.springframework" % "spring-context" % "4.0.7.RELEASE",
  "org.springframework" % "spring-core" % "4.0.7.RELEASE",
  "org.springframework" % "spring-aop" % "4.0.7.RELEASE",
   "mysql" % "mysql-connector-java" % "5.1.18",
  "org.mongodb" % "mongo-java-driver" % "2.12.4",
  "org.jongo" % "jongo" % "1.1"
)
