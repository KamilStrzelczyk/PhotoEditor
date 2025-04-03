plugins {
    id("java")
}
group = "org.ks.photoeditor.feature.editor"

dependencies {
    implementation(project(":core:presentation"))
    implementation(project(":core:domain"))
    implementation("io.reactivex.rxjava3:rxjava:3.1.5")
    implementation("com.google.dagger:dagger:2.50")
    annotationProcessor("com.google.dagger:dagger-compiler:2.50")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}