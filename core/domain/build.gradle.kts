plugins {
    id("java")
}

group = "org.ks.photoeditor.core.domain"

dependencies {
    implementation("io.reactivex.rxjava3:rxjava:3.1.5")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.google.dagger:dagger:2.50")
    annotationProcessor("com.google.dagger:dagger-compiler:2.50")
}

tasks.test {
    useJUnitPlatform()
}