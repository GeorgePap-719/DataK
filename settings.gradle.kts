pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
    val kotlinVersion: String by settings
    val versionUpdatesVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion

        id("com.github.ben-manes.versions") version versionUpdatesVersion
    }
}

rootProject.name = "DataK"

