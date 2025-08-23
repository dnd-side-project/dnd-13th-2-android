plugins {
    id("side.dnd.android.library")
    id("side.dnd.hilt")
    alias(libs.plugins.protobuf)
}

android {
    namespace = "side.dnd.data"
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.bundles.square)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.0"
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}
