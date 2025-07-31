package gradle.convention.plugin.android

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BasePlugin
import gradle.convention.plugin.configure.configureAndroid
import gradle.convention.plugin.configure.configureApplication
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

internal class AndroidApplicationPlugin : Plugin<Project> {
    /**
     * Applies and configures Android, Kotlin, Hilt, and Compose plugins for the target Gradle project.
     *
     * This method ensures the required plugins are applied and invokes additional configuration
     * for plugins of type `BasePlugin` and `AppPlugin`.
     */
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
            apply("side.dnd.hilt")
            apply("side.dnd.compose")
        }

        plugins.withType<BasePlugin>().configureEach {
            configureAndroid()
        }

        plugins.withType<AppPlugin>().configureEach {
            configureApplication()
        }
    }
}
