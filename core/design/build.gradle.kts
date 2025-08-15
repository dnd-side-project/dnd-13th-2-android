plugins {
    id("side.dnd.android.library")
    id("side.dnd.compose")
}

android {
    namespace = "side.dnd.design"

}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.material)
    implementation(libs.kotlinx.collections.immutable)
}