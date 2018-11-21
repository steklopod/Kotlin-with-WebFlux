// Обходной путь, чтобы сделать JUnit платформы Gradle плагин доступен с помощью DSL плагинов

pluginManagement {
    repositories {
        maven("https://jcenter.bintray.com/")
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.junit.platform.gradle.plugin") {
                useModule("org.junit.platform:junit-platform-gradle-plugin:${requested.version}")
            }
        }
    }
}