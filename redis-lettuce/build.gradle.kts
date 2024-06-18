plugins {
    `java-library`
    alias(libs.plugins.spotless) apply true
}


repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.core)
    implementation(libs.lettuce)

    testImplementation(libs.junit.api)
    testImplementation(libs.junit.engine)
}

tasks.withType<Test> {
    useJUnitPlatform()
}