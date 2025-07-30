package gradle.convention.plugin.const

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object VersionConst {
    const val compileSdk = 35
    const val minSdk = 28
    const val targetSdk = 35

    val JAVA_VERSION = JavaVersion.VERSION_17
    val JVM_TARGET = JvmTarget.JVM_17
}