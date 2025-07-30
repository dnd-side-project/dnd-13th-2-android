package gradle.convention.plugin.android

import gradle.convention.plugin.configure.configureAndroidTest
import gradle.convention.plugin.extension.getVersionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal class AndroidFeaturePlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("side.dnd.android.library")
            apply("side.dnd.hilt")
            apply("side.dnd.compose")
        }

        configureAndroidTest()

        val libs = getVersionCatalog()
        dependencies {
            "implementation"(project(":common"))
            "implementation"(project(":domain"))
            "implementation"(project(":data"))
            "implementation"(project(":design"))

            "implementation"(libs.findLibrary("hilt.navigation.compose").get())
            "implementation"(libs.findBundle("lifecycle").get())
            "implementation"(libs.findLibrary("kotlinx-collections-immutable").get())
            "implementation"(libs.findBundle("navigation").get())
            "implementation"(libs.findLibrary("coil").get())
        }
    }
}
