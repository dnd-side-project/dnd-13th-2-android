package gradle.convention.plugin.extension

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

/**
 * Applies the given configuration action to the Android ApplicationExtension of this project.
 *
 * @param action The configuration block to apply to the ApplicationExtension.
 */
internal fun Project.onApplicationExtension(action: ApplicationExtension.() -> Unit) {
    extensions.configure<ApplicationExtension>(action)
}

internal val Project.baseAppModuleException: CommonExtension<*, *, *, *, *, *>
    get() = extensions.getByType<BaseAppModuleExtension>()

internal val Project.libraryExtension: CommonExtension<*, *, *, *, *, *>
    get() = extensions.getByType<LibraryExtension>()

internal val Project.androidExtension get() = runCatching { libraryExtension }
    .recoverCatching { baseAppModuleException }
    .onFailure { println("Could not find Library or Application extension from this project") }
    .getOrThrow()

/**
 * Retrieves the version catalog named "libs" from the project's VersionCatalogsExtension.
 *
 * @return The "libs" version catalog for dependency management.
 */
fun Project.getVersionCatalog() = extensions.getByType<VersionCatalogsExtension>().named("libs")

/**
 * Enables the "ExplicitBackingFields" Kotlin language feature for all source sets in the project.
 */
internal fun KotlinProjectExtension.allowExplicitBackingFields() {
    sourceSets.all {
        languageSettings.enableLanguageFeature("ExplicitBackingFields")
    }
}