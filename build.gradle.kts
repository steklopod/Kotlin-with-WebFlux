import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.kotlin.dsl.version
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.noarg.gradle.NoArgExtension


plugins {
    application
    val kotlinVersion = "1.3.10"
    kotlin("jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("com.github.johnrengelman.shadow") version "2.0.4"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    id("org.springframework.boot") version "2.1.0.RELEASE"
    id("org.junit.platform.gradle.plugin") version "1.0.2"
}

application {
    mainClassName = "functional.Application"
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:2.1.0.RELEASE")
    }
}
configure<NoArgExtension> {
    annotation("org.springframework.data.mongodb.core.mapping.Document")
}


repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    maven("https://repo.spring.io/snapshot")
    maven("https://repo.spring.io/milestone")
}


dependencies {
    //spring webflux
    implementation("org.springframework:spring-webflux")
    implementation("org.springframework:spring-context") {
        exclude(module = "spring-aop")
    }
    implementation("org.springframework.boot:spring-boot-starter-reactor-netty")

    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")

    implementation("io.netty:netty-all:4.1.31.Final")
//    implementation("io.projectreactor.ipc:reactor-netty")

    implementation("com.samskivert:jmustache")

    //spring security for webflux
    implementation("org.springframework.security:spring-security-core")
    implementation("org.springframework.security:spring-security-config")
    implementation("org.springframework.security:spring-security-web")

    //spring data mongodb reactive
    implementation("org.springframework.data:spring-data-mongodb")
    implementation("org.mongodb:mongodb-driver-reactivestreams")

    //spring session
    implementation("org.springframework.session:spring-session-core")

    //kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.0.1")
    //slf4j and logback
    implementation("org.slf4j:slf4j-api")
    implementation("org.slf4j:jcl-over-slf4j")
    implementation("ch.qos.logback:logback-classic")

    implementation("com.google.code.findbugs:jsr305:3.0.2") // Needed for now, could be removed when KT-19419 will be fixed

    //test
    testImplementation("org.springframework:spring-test") {
        exclude(module = "junit")
    }
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    //DataBases
//    runtimeOnly("org.hsqldb:hsqldb")
//    runtimeOnly("mysql:mysql-connector-java")
}
