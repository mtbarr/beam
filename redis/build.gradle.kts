plugins {
    `java-library`
    alias(libs.plugins.spotless) apply true
}


repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.core)
    implementation(libs.jedis)
}