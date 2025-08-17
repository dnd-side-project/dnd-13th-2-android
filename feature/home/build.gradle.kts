import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("side.dnd.feature")
}

android {
    namespace = "side.dnd.feature.home"

    defaultConfig {
        buildConfigField("String","NAVER_MAP_KEY",getLocalKey("NCP_KEY_ID"))
    }
}

dependencies {
    implementation(project(":feature:core"))
    implementation(libs.naver.map.compose)
    implementation(libs.naver.map.location)
}

fun getLocalKey(propertyKey:String):String{
    return gradleLocalProperties(rootDir, providers).getProperty(propertyKey)
}