package gradle.convention.plugin.android

import gradle.convention.plugin.configure.configureAndroidTest
import gradle.convention.plugin.extension.getVersionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import kotlin.text.get

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
            "implementation"(project(":core:common"))
            "implementation"(project(":core:domain"))
            "implementation"(project(":core:data"))
            "implementation"(project(":core:design"))
            "implementation"(project(":core:local"))

            "implementation"(libs.findLibrary("hilt.navigation.compose").get())
            "implementation"(libs.findBundle("lifecycle").get())
            "implementation"(libs.findLibrary("kotlinx-collections-immutable").get())
            "implementation"(libs.findLibrary("coil").get())
        }
    }
}
