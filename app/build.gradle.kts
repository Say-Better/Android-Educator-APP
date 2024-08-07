plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")


}

android {
    namespace = "com.example.saybettereducator"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.saybettereducator"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.database.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation ("androidx.compose.foundation:foundation:1.7.0-alpha02")

    // 바텀네비 종속성
    implementation("androidx.navigation:navigation-compose:2.7.4")


    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

    // Add the dependency for the Firebase SDK for Google Analytics
    implementation("com.google.firebase:firebase-analytics-ktx")

    //Hilt 종속성 추가
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("com.google.dagger:hilt-android:2.46")
    kapt("com.google.dagger:hilt-android-compiler:2.46")

    //webRTC 종속성 추가
    implementation ("com.mesibo.api:webrtc:1.0.5")

    //Gson 종속성 추가
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("io.coil-kt:coil-compose:2.1.0")

    // Orbit
    implementation("org.orbit-mvi:orbit-core:9.0.0")
    implementation("org.orbit-mvi:orbit-viewmodel:9.0.0")
    implementation("org.orbit-mvi:orbit-compose:9.0.0")
    testImplementation("org.orbit-mvi:orbit-test:9.0.0")
}
