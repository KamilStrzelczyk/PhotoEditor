plugins {
    id("java")
    id("com.diffplug.spotless") version "6.25.0"
}

group = "org.ks.photoeditor.core.infrastructure"

spotless {
    java {
        googleJavaFormat()

    }
}

dependencies {
    implementation("io.reactivex.rxjava3:rxjava:3.1.5")
    implementation(project(":core:domain"))
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.google.dagger:dagger:2.50")
    annotationProcessor("com.google.dagger:dagger-compiler:2.50")
}

tasks.test {
    useJUnitPlatform()
}
repositories {
    mavenCentral()
}
