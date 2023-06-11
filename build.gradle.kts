plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.21"
    id("org.jetbrains.intellij") version "1.14.1"
}

group = "com.github.kornilova203"
version = "0.8.5-SNAPSHOT"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.1.2")
    type.set("IC") // Target IDE Platform
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("231")
    }
}

sourceSets {
    main {
        java.srcDirs("src", "gen")
        resources.srcDirs("resources")
    }
    test {
        java.srcDirs("tests")
        resources.srcDirs("testData")
    }
}


idea {
    module {
        generatedSourceDirs = setOf(file("gen"))
    }
}

