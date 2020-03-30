/**
  * Copyright (C) 2003-2020, e-Evolution Consultants S.A. , http://www.e-evolution.com
  * This program is free software: you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published by
  * the Free Software Foundation, either version 3 of the License, or
  * (at your option) any later version.
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  * You should have received a copy of the GNU General Public License
  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
  * Email: victor.perez@e-evolution.com, http://www.e-evolution.com , http://github.com/e-Evolution
  * Created by victor.perez@e-evolution.com , www.e-evolution.com
  **/

name := "org.eevolution.PaymentProcessor"
version := "0.1"
fork := true
fork in Test := true

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-encoding", "utf8")

lazy val commonSettings = Seq(
  version := "0.1-SNAPSHOT",
  organization := "org.eevolution",
  scalaVersion := "2.13.1"
)
scalaVersion := "2.13.1"
libraryDependencies ++= Seq(
  "com.stripe" % "stripe-java" % "18.7.0",
  "dev.zio" %% "zio" % "1.0.0-RC18-2",
  "dev.zio" %% "zio-test"     % "1.0.0-RC18-2" % "test",
  "dev.zio" %% "zio-test-sbt" % "1.0.0-RC18-2" % "test",
  "io.getquill" %% "quill-jdbc" % "3.5.0",
  "org.postgresql" % "postgresql" % "42.2.8"
)

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.21"
libraryDependencies += "org.slf4j" % "slf4j-jdk14" % "1.7.21"
libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.21"

testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))

ThisBuild / useCoursier := false
ThisBuild / turbo := true

Compile / classLoaderLayeringStrategy := ClassLoaderLayeringStrategy.ScalaLibrary
// enabled with turbo

Test / classLoaderLayeringStrategy := ClassLoaderLayeringStrategy.ScalaLibrary
// enabled with turbo


val sourceAdempiere = "/Users/e-Evolution/Develop/ADempiere/develop"

unmanagedJars in Compile ++= (file(sourceAdempiere + "/lib") * "*.jar").classpath
unmanagedJars in Compile ++= (file(sourceAdempiere + "/packages") * "*.jar").classpath
unmanagedJars in Compile ++= (file("lib") * "*.jar").classpath

unmanagedBase := baseDirectory.value / "lib"
unmanagedClasspath in Compile += file(sourceAdempiere + "/bin")
unmanagedClasspath in Compile += file(sourceAdempiere + "/zkwebui/WEB-INF/classes")
//unmanagedClasspath in Compile += file(sourceAdempiere + "/target/scala-2.11/classes")
//unmanagedClasspath in Compile += file(sourceAdempiere + "/target/scala-2.11/test-classes")

//unmanagedJars in Compile += Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/jfxrt.jar"))

unmanagedJars in Compile ++= (file(sourceAdempiere + "/zkwebui/WEB-INF/lib") * "*.jar").classpath
unmanagedJars in Compile ++= (file(sourceAdempiere + "/tools/lib") * "*.jar").classpath
unmanagedJars in Compile ++= (file(sourceAdempiere + "/lib") * "*.jar").classpath
unmanagedJars in Compile ++= (file(sourceAdempiere + "/packages") * "*.jar").classpath
unmanagedJars in Compile ++= (file(sourceAdempiere + "/zkpackages") * "*.jar").classpath

scalaSource in Compile := baseDirectory.value / "src" / "main" / "scala"
scalaSource in Test := baseDirectory.value  / "src" / "test" / "scala"


assemblyJarName in assembly := "org.eevolution.PaymentProcessor.jar"

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = true, includeDependency = false)

lazy val PaymentProcessor = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "sbt-something"
  )
