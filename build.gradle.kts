
plugins {
    alias(libs.plugins.spotless) apply false
}

group = "io.github.mtbarr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


subprojects {
    group = rootProject.group
    version = rootProject.version
}