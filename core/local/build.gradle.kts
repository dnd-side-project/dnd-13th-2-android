plugins {
    id("side.dnd.android.library")
    id("side.dnd.hilt")
}

android {
    namespace = "side.dnd.core.local"
}

dependencies {
    implementation(project(":core:common"))
    
    implementation(libs.room.runtime)
    implementation(libs.room.common)
    ksp(libs.room.compiler)
    
}
