package gradle.convention.plugin.android

import gradle.convention.plugin.extension.getVersionCatalog
import gradle.convention.plugin.extension.onApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal class AndroidComposePlugin : Plugin<Project> {

    /**
     * Configures the target Android project for Jetpack Compose by enabling Compose build features, applying necessary plugins, and adding Compose-related dependencies using the version catalog.
     *
     * This setup includes the Compose BOM, core Compose libraries, UI tooling for debug builds, navigation, adaptive Compose libraries, and Kotlin serialization support.
     */
    override fun apply(target: Project) = with(target) {
        val libs = getVersionCatalog()

        onApplicationExtension {
            buildFeatures {
                compose = true
            }
        }

        with(pluginManager) {
            apply("org.jetbrains.kotlin.plugin.compose")
            apply("kotlinx-serialization")
            apply("kotlin-parcelize")
        }

        dependencies {
            val bom = libs.findLibrary("compose-bom").get()
            "implementation"(platform(bom))
            "androidTestImplementation"(platform(bom))

            "implementation"(libs.findBundle("compose").get())
            "debugImplementation"(libs.findLibrary("compose.ui.tooling").get())

            "implementation"(libs.findLibrary("navigation.compose").get())
            "implementation"(libs.findBundle("composeAdaptive").get())
            "implementation"(libs.findLibrary("kotlinx-serialization-json").get())
        }
    }
}
