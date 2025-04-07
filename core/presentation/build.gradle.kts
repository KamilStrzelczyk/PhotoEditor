plugins {
    id("java")
    id("com.diffplug.spotless") version "6.25.0"
}

group = "org.ks.photoeditor.core.presentation"

spotless {
    java {
        googleJavaFormat()

    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}