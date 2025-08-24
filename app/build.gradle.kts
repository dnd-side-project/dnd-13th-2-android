import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("side.dnd.android.application")
}

android {
    namespace = "side.dnd.app"

    defaultConfig {
        applicationId = "side.dnd.app"
        versionCode = 1
        versionName = "1.0.0"
        
        buildConfigField("String", "BASE_URL", "\"${project.findProperty("API_BASE_URL") ?: "https://api.eodigo.com/"}\"")
    }

}

fun getLocalKey(propertyKey:String):String{
    return gradleLocalProperties(rootDir, providers).getProperty(propertyKey)
}

dependencies {
    implementation(project(":core:design"))
    implementation(project(":feature:core"))
    implementation(project(":feature:home"))
    implementation(project(":feature:price_rank"))
    implementation(libs.activity.compose)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.square)
    implementation(libs.kotlinx.collections.immutable)
}