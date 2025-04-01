plugins {
    id("java")
}
group = "org.ks.photoeditor.feature.init"

dependencies {
    implementation(project(":core:presentation"))
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}