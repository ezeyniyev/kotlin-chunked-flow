import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.4.31"
}

group = "pl.doz.kotlin"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	jcenter()
}


dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.31")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.31")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
