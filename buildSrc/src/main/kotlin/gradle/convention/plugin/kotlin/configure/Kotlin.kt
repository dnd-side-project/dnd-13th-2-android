package gradle.convention.plugin.kotlin.configure

import gradle.convention.plugin.const.VersionConst
import gradle.convention.plugin.extension.allowExplicitBackingFields
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configures the project for Kotlin JVM compilation with standardized Java and Kotlin compiler settings.
 *
 * Sets the Java source and target compatibility, configures all Kotlin compile tasks to use a specified JVM target, and enables explicit backing fields in the Kotlin DSL extension.
 */
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