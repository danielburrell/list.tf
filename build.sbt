name := "wanted"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.2.3",
  "org.expressme" % "JOpenId" % "1.08",
  "org.springframework" % "spring-jdbc" % "3.2.4.RELEASE",
  "org.springframework" % "spring-core" % "3.2.4.RELEASE",
  "org.springframework" % "spring-web" % "3.2.4.RELEASE",
   "mysql" % "mysql-connector-java" % "5.1.18"
)


play.Project.playJavaSettings
