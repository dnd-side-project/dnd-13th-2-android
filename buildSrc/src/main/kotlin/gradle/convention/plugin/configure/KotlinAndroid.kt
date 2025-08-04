package gradle.convention.plugin.configure

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import gradle.convention.plugin.const.VersionConst
import gradle.convention.plugin.const.VersionConst.compileSdk
import gradle.convention.plugin.const.VersionConst.minSdk
import gradle.convention.plugin.extension.allowExplicitBackingFields
import gradle.convention.plugin.extension.androidExtension
import gradle.convention.plugin.extension.onApplicationExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

internal fun Project.configureApplication() {
    onApplicationExtension {
        defaultConfig {
            targetSdk = 35
        }

        buildTypes {
            debug {
                isMinifyEnabled = false
                isShrinkResources = false
            }
            release {
                isMinifyEnabled = true
                isShrinkResources = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }
}

internal fun Project.configureAndroid() {
    androidExtension.apply {
        compileSdk = VersionConst.compileSdk

        defaultConfig {
            minSdk = VersionConst.minSdk

            vectorDrawables.useSupportLibrary = true
        }

        compileOptions {
            sourceCompatibility = VersionConst.JAVA_VERSION
            targetCompatibility = VersionConst.JAVA_VERSION
        }

        buildFeatures {
            buildConfig = true
        }

        kotlinExtension.apply {
            jvmToolchain(17)
            allowExplicitBackingFields()
        }
    }
}