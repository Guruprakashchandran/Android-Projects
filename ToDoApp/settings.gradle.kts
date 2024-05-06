pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven{
            url = uri("https://maven.zohodl.com/")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven{
            url = uri("https://maven.zohodl.com/")
        }
        maven("https://jitpack.io")
    }
}

rootProject.name = "ToDo App"
include(":app")
 