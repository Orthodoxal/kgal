plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm()
    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":kgal-core"))
            }
        }
    }
}
