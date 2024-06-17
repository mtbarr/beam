plugins {
    `java-library`
    alias(libs.plugins.spotless) apply true
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.jetbrains.annotations)
    implementation(libs.bundles.test)
}