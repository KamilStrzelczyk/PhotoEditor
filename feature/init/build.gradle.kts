plugins {
    id("java")
}
group = "org.ks.photoeditor.feature.init"

dependencies {
    implementation(project(":core:presentation"))
    implementation(project(":feature:editor"))
    implementation("com.google.dagger:dagger:2.50")
    annotationProcessor("com.google.dagger:dagger-compiler:2.50")
    implementation(project(":core:domain"))
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}