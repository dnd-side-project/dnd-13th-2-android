plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.gradle.android)
    implementation(libs.gradle.kotlin)
    implementation(libs.gradle.kotlin.compose)
    implementation(libs.gradle.hilt)
    implementation(libs.gradle.google.devtools.ksp)
    implementation(libs.gradle.kotlin.serialization)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "side.dnd.android.application"
            implementationClass = "gradle.convention.plugin.android.AndroidApplicationPlugin"
        }
        register("androidLibrary") {
            id = "side.dnd.android.library"
            implementationClass = "gradle.convention.plugin.android.AndroidLibraryPlugin"
        }
        register("androidHilt") {
            id = "side.dnd.hilt"
            implementationClass = "gradle.convention.plugin.android.AndroidHiltPlugin"
        }
        register("androidCompose") {
            id = "side.dnd.compose"
            implementationClass = "gradle.convention.plugin.android.AndroidComposePlugin"
        }
        register("androidFeature") {
            id = "side.dnd.feature"
            implementationClass = "gradle.convention.plugin.android.AndroidFeaturePlugin"
        }
        register("kotlinLibrary") {
            id = "side.dnd.kotlin.library"
            implementationClass = "gradle.convention.plugin.kotlin.KotlinLibraryPlugin"
        }
    }
}