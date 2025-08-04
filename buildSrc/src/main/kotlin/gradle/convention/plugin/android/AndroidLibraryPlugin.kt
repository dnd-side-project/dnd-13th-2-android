package gradle.convention.plugin.android

import com.android.build.gradle.BasePlugin
import gradle.convention.plugin.configure.configureAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

internal class AndroidLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
        }

        plugins.withType<BasePlugin>().configureEach {
            configureAndroid()
        }
    }
}
