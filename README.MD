# Still under development!
# Beam

Beam is an implementation-agnostic messaging library designed to provide a core framework for message handling. This allows for easy integration with various messaging systems such as Redis Pub/Sub, RabbitMQ, Amazon SNS, and more. The library is designed to be flexible, making it easy to switch between different messaging systems without changing your core application logic.

## Features

- **Implementation Agnostic**: Integrate seamlessly with various messaging systems.
- **Message Adapters**: Create and manage message adapters for different message types efficiently.
- **Subscription Management**: Register and manage subscribers for various message types.

## Installation

Add the necessary dependency to your project. For Maven, include the dependency in your `pom.xml`. 
For Gradle, add it to your `build.gradle` file,
For Gradle (Kotlin DSL), add it to your `build.gradle.kts` file.

### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>io.github.matbarr</groupId>
        <artifactId>beam</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

### Gradle
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'io.github.matbarr:beam:1.0.0'
}
```

### Gradle (Kotlin DSL)
```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("io.github.matbarr:beam:1.0.0")
}
```


### Prerequisites

- Java 8 or higher

### Usage

1. **Creating a Message Adapter**: Implement the `MessageAdapter` interface for your specific message type.
2. **Registering a Message Adapter**: Register your message adapter with the `BeamMessageBroker`.
3. **Publishing Messages**: Use the `BeamMessageBroker` to publish messages to subscribers.
4. **Subscribing to Messages**: Register subscribers for specific message types with the `BeamMessageBroker`.
5. **Unsubscribing from Messages**: Manage subscriptions by unsubscribing when necessary.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.txt) file for details.
