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
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://repository.map.naver.com/archive/maven")
        }
    }
}

rootProject.name = "Sideproject"
include(":app")
include(":core:data")
include(":core:design")
include(":core:common")
include(":core:domain")
include(":core:local")
include(":feature:core")
include(":feature:home")
include(":feature:price_rank")
