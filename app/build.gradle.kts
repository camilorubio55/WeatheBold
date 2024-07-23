plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.safe.args)
    libs.plugins.kotlinSerialization.get().pluginId
    kotlin("kapt")
}

android {
    namespace = "com.example.weather"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weather"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    dataBinding {
        enable = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.hilt.android)
    implementation(libs.squareup.retrofit)
    implementation(libs.gson)
    implementation(libs.gsonConverter)
    implementation(libs.kotlinx.serialization)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.okhttp.interceptor)
    implementation(libs.androidx.recyclerview.layout)
    implementation(libs.bumptech.glide)
    testImplementation(libs.mockitoKotlin)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.kotlinxCoroutinesTest)
    api(libs.androidx.arch.core.testing)
    kapt(libs.bumptech.glide.ksp)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
