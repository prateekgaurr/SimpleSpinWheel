pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(java.net.URI.create("https://jitpack.io"))
    }
}

rootProject.name = "WheelSpinner"
include(":app")
include(":SimpleSpinWheel")
