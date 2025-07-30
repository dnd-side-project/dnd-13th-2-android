plugins {
    id("side.dnd.android.library")
}

android {
    namespace = "side.dnd.domain"
}

dependencies {
    implementation(project(":data"))
    implementation(project(":common"))
}
