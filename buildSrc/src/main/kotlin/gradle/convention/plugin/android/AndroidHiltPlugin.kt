package gradle.convention.plugin.android

import gradle.convention.plugin.extension.getVersionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal class AndroidHiltPlugin : Plugin<Project> {

    /**
     * Configures the target Gradle project with Hilt and KSP integration.
     *
     * Applies the Hilt Android and Kotlin Symbol Processing plugins, and adds the necessary Hilt runtime and compiler dependencies using the project's version catalog.
     */
    override fun apply(target: Project) = with(target) {
        val libs = getVersionCatalog()

        with(pluginManager) {
            apply("com.google.dagger.hilt.android")
            apply("com.google.devtools.ksp")
        }

        dependencies {
            "implementation"(libs.findLibrary("dagger.hilt.android").get())
            "ksp"(libs.findLibrary("dagger.hilt.android.compiler").get())
        }
    }
}
