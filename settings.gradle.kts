rootProject.name = "PhotoEditor"

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

fun String.register() {
    file(this).listFiles()!!.forEach { file ->
        if (file.isDirectory) {
            file.listFiles()!!.forEach { file2 ->
                if (file2.isDirectory) {
                    file2.listFiles()!!.forEach { file3 ->
                        if (file3.name == "build.gradle.kts") {
                            include(":$this:${file.name}:${file2.name}")
                        }
                    }
                } else {
                    if (file2.name == "build.gradle.kts") {
                        include(":$this:${file.name}")
                    }
                }
            }
        }
    }
}

include(":app")
"feature".register()
"core".register()
