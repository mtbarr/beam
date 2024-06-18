rootProject.name = "beam"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
dependencyResolutionManagement {
    repositories {
        mavenCentral()

    }

    versionCatalogs {
        register("libs") {
            from(files("./libs.versions.toml"))
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

include(
    "core",
    "redis-lettuce",
    "rabbitmq",
    "sns"
)