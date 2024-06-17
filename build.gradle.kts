plugins {
    id("java")
}

group = "io.github.mtbarr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation(platform("org.junit:junit-bom:5.10.0"))
        testImplementation("org.junit.jupiter:junit-jupiter")

        implementation("org.jetbrains:annotations:13.0")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}