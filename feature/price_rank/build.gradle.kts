import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("side.dnd.feature")
}

android {
    namespace = "com.side.dnd.feature.price_rank"
    compileSdk = 35

}

dependencies {
    implementation(project(":feature:core"))

    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m3)

}
