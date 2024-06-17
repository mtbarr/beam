
repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation("redis.clients:jedis:3.7.0")
}