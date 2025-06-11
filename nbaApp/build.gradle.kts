import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ksp)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.glide)
            implementation(libs.glide.landscapist)
            implementation(libs.koin.android)
            implementation(libs.moshi.kotlin)
            implementation(libs.okhttp)
            implementation(libs.okhttp.logging.interceptor)
            implementation(libs.retrofit)
            implementation(libs.retrofit.converter.moshi)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.navigation.compose)
        }

        androidUnitTest {
            dependencies {
                implementation(libs.testing.kotlinx.coroutines.test)
                implementation(libs.testing.kotlin.test)
                implementation(libs.testing.mockk)
            }
        }

    }
}

android {
    namespace = "net.rjanda.casestudy.nba"
    compileSdk = 35

    dependencies {
        ksp(libs.moshi.kotlin.codegen)
    }

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "net.rjanda.casestudy.nba"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(FileInputStream(localPropertiesFile))
        }
        val apiKey = localProperties.getProperty("API_KEY")
        if (apiKey.isNullOrBlank()) {
            throw GradleException("API_KEY not set! Set API_KEY in local.properties file.")
        }
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.androidx.compose.ui.tooling)
}
