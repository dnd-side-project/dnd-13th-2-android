package gradle.convention.plugin.kotlin.configure

import gradle.convention.plugin.const.VersionConst
import gradle.convention.plugin.extension.allowExplicitBackingFields
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinJVM() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = VersionConst.JAVA_VERSION
        targetCompatibility = VersionConst.JAVA_VERSION
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(VersionConst.JVM_TARGET)
        }
    }

    kotlinExtension.apply {
        allowExplicitBackingFields()
    }
}