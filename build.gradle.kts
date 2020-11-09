plugins {
  kotlin("jvm") version "1.4.0"
}

group = "com.itemcarts"
version = "1.0-SNAPSHOT"
val runeLiteVersion = "1.6.38"

repositories {
  mavenLocal()
  mavenCentral {
    setUrl("https://repo.runelite.net")
  }
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib"))

  compileOnly("net.runelite:client:$runeLiteVersion")

  compileOnly("org.projectlombok:lombok:1.18.4")
  annotationProcessor("org.projectlombok:lombok:1.18.4")

  testImplementation("org.slf4j:slf4j-simple:1.7.12")
  testImplementation("net.runelite:client:$runeLiteVersion") {
    exclude(group = "ch.qos.logback", module = "logback-classic")
  }
}

tasks.compileKotlin {
  kotlinOptions {
    jvmTarget = "1.8"
    freeCompilerArgs = listOf("-Xjvm-default=enable")
  }
}
