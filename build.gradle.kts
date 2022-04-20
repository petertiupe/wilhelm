@file:Suppress("UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform") version "1.6.10"
    // Kotlin-Symbol-Processor-support KSP
    id("com.google.devtools.ksp") version "1.6.10-1.0.2"

    // Kotlin-Serialization (not necessary for fritz2 but useful)
    kotlin("plugin.serialization") version "1.6.10"
}

repositories {
    // mavenLocal()
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

val fritz2Version = "1.0-SNAPSHOT"

//group = "my.fritz2.app"
//version = "0.0.1-SNAPSHOT"

kotlin {
    jvm()
    js(IR) {
        browser()
    }.binaries.executable()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("dev.fritz2:core:$fritz2Version")
                // implementation("dev.fritz2:headless:$fritz2Version") // optional

                // kotlinx-Serialization needs the plugin and this dependency
                // versions of plugin and dependency are independent
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

            }
        }
        val jvmMain by getting {
            dependencies {
            }
        }
        val jsMain by getting {
            dependencies {
            }
        }
    }
}

/**
 * KSP support - start
 */
dependencies {
    add("kspMetadata", "dev.fritz2:lenses-annotation-processor:$fritz2Version")
}
kotlin.sourceSets.commonMain { kotlin.srcDir("build/generated/ksp/commonMain/kotlin") }
tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().all {
    if (name != "kspKotlinMetadata") dependsOn("kspKotlinMetadata")
}
// needed to work on Apple Silicon. Should be fixed by 1.6.20 (https://youtrack.jetbrains.com/issue/KT-49109#focus=Comments-27-5259190.0-0)
rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().nodeVersion = "16.0.0"
}
/**
 * KSP support - end
 */