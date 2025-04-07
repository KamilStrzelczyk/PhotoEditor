import org.gradle.internal.impldep.bsh.commands.dir

plugins {
    id("java")
    id("checkstyle")
    id("com.diffplug.spotless") version "6.25.0"
}

group = "org.ks.photoeditor.app"
version = "1.0-SNAPSHOT"

checkstyle {
    toolVersion = "10.21.4"
    configFile = file("${rootProject.projectDir}/checkstyle.xml")
}

spotless {
    java {
        googleJavaFormat()

    }
}
tasks.named("spotlessApply") {
    mustRunAfter("checkstyleMain")
}
dependencies {
    implementation("com.formdev:flatlaf:3.5.4")
    implementation(project(":feature:init"))
    implementation(project(":feature:editor"))
    implementation(project(":core:domain"))
    implementation(project(":core:presentation"))
    implementation(project(":core:infrastructure"))
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.google.dagger:dagger:2.50")
    annotationProcessor("com.google.dagger:dagger-compiler:2.50")
}

tasks.test {
    useJUnitPlatform()
}