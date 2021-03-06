@file:Suppress("UNUSED_VARIABLE")

import kotlin.collections.mutableMapOf

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
        browser {
            runTask {
                // Die WEBPACK-Proxy-Dev-Server-Dokumentation findet man unter folgender URL
                // https://webpack.js.org/configuration/dev-server/#devserverproxy
                devServer = devServer?.copy(
                    port = 9001,
                    proxy = mutableMapOf(
                        "/repozugriff" to mapOf(
                            "target" to "https://api.corona-zahlen.org",
                            // "secure" to false,
                            //"changeOrigin" to true
                            "logLevel" to "debug",
                            "pathRewrite" to ( "/repozugriff" to "/germany" ),
                            "secure" to false,
                            "changeOrigin" to true,
                        ),
                        "/germany" to mapOf(
                            "target" to "https://api.corona-zahlen.org",
                            // "secure" to false,
                            //"changeOrigin" to true
                            "logLevel" to "debug",
                            "pathRewrite" to ( "^/germany" to "/germany" ),
                            "secure" to false,
                            "changeOrigin" to true,
                        ),
                    )
                )

                /*
                * target: "https://kons.sfa.oeffentliche.de",
               secure: false,
                changeOrigin: true,
                agent: proxyAgent,
                headers: {Cookie: cookie},
                logLevel: 'debug'
                * */
                /*devServer = devServer?.copy(
                    port = 9000,
                    proxy = mutableMapOf(
                        "/tiupe" to mapOf(
                            // "target" to "https://api.corona-zahlen.org",
                            "target" to "http://www.tiupe.de/index.html",
                            // "pathRewrite" to ("^/tiupe" to ""),
                            "secure" to false
                        )
                    )
                ) */
            }
        }
    }.binaries.executable()

    sourceSets {
        val commonMain by getting {
            dependencies {

                /** Nur eine der beiden fritz2-Komponenten muss aktiv sein */

                // fritz2 ohne Headless-Komponenten
                // implementation("dev.fritz2:core:$fritz2Version")

                // fritz2 mit Headless-Komponenten
                implementation("dev.fritz2:headless:$fritz2Version")

                // kotlinx-Serialization needs the plugin and this dependency
                // versions of plugin and dependency are independent
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

                // Datetime f??r die RKI-Daten
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
            }
        }
        val jvmMain by getting {
            dependencies {
            }
        }
        val jsMain by getting {
            dependencies {
                // tailwind
                implementation(npm("tailwindcss", "3.0.19"))
                // implementation(npm("@tailwindcss/forms", "0.4.0")) // optional

                // webpack
                implementation(devNpm("postcss", "8.4.6"))
                implementation(devNpm("postcss-loader", "6.2.1"))
                implementation(devNpm("autoprefixer", "10.4.2"))
                implementation(devNpm("css-loader", "6.6.0"))
                implementation(devNpm("style-loader", "3.3.1"))
                implementation(devNpm("cssnano", "5.0.17"))
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