pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://maven.fabricmc.net/")
    }

    includeBuild("build-logic")
}

rootProject.name = "guithium"

include("api")
include("bukkit")
include("fabric")
