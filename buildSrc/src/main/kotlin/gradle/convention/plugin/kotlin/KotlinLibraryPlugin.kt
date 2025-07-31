package gradle.convention.plugin.kotlin

import gradle.convention.plugin.kotlin.configure.configureKotlinJVM
import gradle.convention.plugin.kotlin.configure.configureKotlinTest
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinLibraryPlugin: Plugin<Project> {
    /**
     * Applies the Kotlin JVM plugin and configures Kotlin JVM and test settings for the target Gradle project.
     *
     * This sets up the project for Kotlin library development by applying necessary plugins and configurations.
     */
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.jvm")

            configureKotlinJVM()
            configureKotlinTest()
        }
    }
}