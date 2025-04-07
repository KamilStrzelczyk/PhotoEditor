plugins {
    id("java")
}

group = "org.ks.photoeditor.core.db"

dependencies {
    implementation("org.xerial:sqlite-jdbc:3.49.1.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}