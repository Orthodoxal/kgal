import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.maven.publish)
}

kotlin {
    jvm()
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
        publishLibraryVariants("release", "debug")
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "test-library"
            isStatic = true
        }
    }
    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "io.github.orthodoxal"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

mavenPublishing {
    val version: String by project

    coordinates(
        groupId = "io.github.orthodoxal",
        artifactId = "test-library",
        version = version,
    )

    pom {
        name.set("Test library for KMP")
        description.set("New test library for different platforms")
        inceptionYear.set("2024")
        url.set("https://github.com/Orthodoxal/test-kmp-library")

        licenses {
            license {
                name.set("The Apache Software License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("orthodoxal")
                name.set("Dmitriy Ilbekov")
                email.set("dimariktron@mail.ru")
            }
        }

        scm {
            url.set("https://github.com/Orthodoxal/test-kmp-library")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()
}
