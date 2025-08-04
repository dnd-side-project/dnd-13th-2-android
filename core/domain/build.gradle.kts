plugins {
    id("side.dnd.android.library")
}

android {
    namespace = "side.dnd.domain"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:common"))
}
