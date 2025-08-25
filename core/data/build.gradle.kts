import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("side.dnd.android.library")
    id("side.dnd.hilt")
    alias(libs.plugins.protobuf)
}

android {
    namespace = "side.dnd.data"

    defaultConfig {
        buildConfigField("String", "BASE_URL", getLocalKey("BASE_URL"))
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:local"))

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

fun getLocalKey(propertyKey:String):String{
    return gradleLocalProperties(rootDir, providers).getProperty(propertyKey)
}
