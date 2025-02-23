plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dagger.hilt.gradle)
    kotlin("kapt")
}

android {
    namespace = "nl.inholland.healthysnackapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "nl.inholland.healthysnackapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Default
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Exercise 2
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.dagger.hilt.core)
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.kotlinx.serialization.json)

    // Exercise 4
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.moshi)
    implementation(libs.moshi.adapters)
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)

    // Exercise 5
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    implementation(libs.coil)
    implementation(libs.coil.compose)

    // Exercise 6
    implementation(libs.androidx.datastore.preferences)

    // Additional
    implementation(libs.jackson.module.kotlin)
    implementation(libs.jackson.databind)
    implementation(libs.kotlin.stdlib)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)
    implementation(libs.camera.core)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)
    implementation(libs.mlkit.barcode.scanning)
    implementation(libs.camera.camera2)
    implementation(libs.androidx.compose.icons.extended)
}