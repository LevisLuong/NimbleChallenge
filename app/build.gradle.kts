
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    id("kotlin-parcelize")
}

val localPropertiesFile = rootProject.file("local.properties")
val localProperties = Properties()
localProperties.load(FileInputStream(localPropertiesFile))

val signingPropertiesFile = rootProject.file("signing.properties")
val signingProperties = Properties()
signingProperties.load(FileInputStream(signingPropertiesFile))

android {
    namespace = "com.levis.nimblechallenge"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.levis.nimblechallenge"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("../keystore/debug.keystore")
            storePassword = "123456"
            keyAlias = "debugkey"
            keyPassword = "123456"
        }
        create("release") {
            try {
                storeFile = file("../keystore/release.keystore")
                storePassword = signingProperties.getProperty("KEYSTORE_PASSWORD") as String
                keyAlias = signingProperties.getProperty("KEY_ALIAS") as String
                keyPassword = signingProperties.getProperty("KEY_PASSWORD") as String
            } catch (ex: Exception) {
                throw InvalidUserDataException("You should define KEYSTORE_PASSWORD and KEY_PASSWORD in gradle.properties.")
            }
        }
    }

    flavorDimensions.add("environment")
    productFlavors {
        create("dev") {
            dimension = flavorDimensions[0]
            applicationIdSuffix = ".dev"

            buildConfigField("String", "CLIENT_ID", localProperties.getProperty("CLIENT_ID_DEV"))
            buildConfigField(
                "String",
                "CLIENT_SECRET",
                localProperties.getProperty("CLIENT_SECRET_DEV")
            )
            buildConfigField(
                "String", "APIUrl", "\"https://nimble-survey-web-staging.herokuapp.com/\""
            )
        }
        create("prod") {
            dimension = flavorDimensions[0]
            buildConfigField("String", "CLIENT_ID", localProperties.getProperty("CLIENT_ID"))
            buildConfigField(
                "String",
                "CLIENT_SECRET",
                localProperties.getProperty("CLIENT_SECRET")
            )
            buildConfigField("String", "APIUrl", "\"https://survey-api.nimblehq.co/\"")
        }
    }
    buildTypes {
        debug{
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    packaging {
        resources.excludes.add("META-INF/*")
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Test
    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.android)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotest)
    testImplementation(libs.turbine)

    // Paging
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    testImplementation(libs.androidx.paging.common)
    testImplementation(libs.androidx.paging.testing)

    // Room DB
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.paging)
    testImplementation(libs.androidx.room.testing)
    ksp(libs.androidx.room.compiler)

    // Encryted DataStore
    implementation(libs.preference.ktx)
    implementation(libs.security.crypto)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.dagger.compiler)
    ksp(libs.hilt.hilt.compiler)

    // Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)

    // OkHttp
    implementation(libs.okhttp.logging)

    // Coil
    implementation(libs.coil.compose)

    // UI
    implementation(libs.info.bar.compose)
}