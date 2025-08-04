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

internal fun Project.onApplicationExtension(action: ApplicationExtension.() -> Unit) {
    extensions.configure<ApplicationExtension>(action)
}

internal val Project.baseAppModuleExtension: CommonExtension<*, *, *, *, *, *>
    get() = extensions.getByType<BaseAppModuleExtension>()

internal val Project.libraryExtension: CommonExtension<*, *, *, *, *, *>
    get() = extensions.getByType<LibraryExtension>()

internal val Project.androidExtension
    get() = runCatching { libraryExtension }
        .recoverCatching { baseAppModuleExtension }
        .onFailure { println("Could not find Library or Application extension from this project") }
        .getOrThrow()

fun Project.getVersionCatalog() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun KotlinProjectExtension.allowExplicitBackingFields() {
    sourceSets.all {
        languageSettings.enableLanguageFeature("ExplicitBackingFields")
    }
}