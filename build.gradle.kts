import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.gradle.kotlin.dsl.withType

plugins {
    alias(libs.plugins.protobuf) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.detekt) apply true
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom("$projectDir/config/detekt/detekt.yml")
    //baseline = file("$projectDir/config/baseline.xml")
    ignoreFailures = false
}

tasks.withType<Detekt>().configureEach {
    reports {
        xml.required.set(true)
        xml.outputLocation.set(file("build/reports/detekt/detekt.xml"))
        sarif.required.set(true)
        html.required.set(false)
        md.required.set(false)
    }

    jvmTarget = "1.8"
}

tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}

dependencies {
    detektPlugins(libs.detekt.formatting)
}