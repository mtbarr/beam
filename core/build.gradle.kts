plugins {
    `java-library`
    alias(libs.plugins.spotless) apply true
}

repositories {
    mavenCentral()
}

dependencies {
    api(libs.jetbrains.annotations)
    testImplementation(libs.bundles.test)
}

tasks.withType<Test> {
    useJUnitPlatform()
}