package gradle.convention.plugin.android

import com.android.build.gradle.BasePlugin
import gradle.convention.plugin.extension.androidExtension
import gradle.convention.plugin.extension.getVersionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

internal class AndroidComposePlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val libs = getVersionCatalog()

        plugins.withType<BasePlugin>().configureEach {
            androidExtension.apply {
                buildFeatures {
                    compose = true
                }
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
