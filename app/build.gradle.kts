import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id ("kotlin-kapt")
    id ("kotlin-parcelize")
    id ("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.1.10"
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.shopapps"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.shopapps"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val localProperties = Properties()
    val localPropertiesFile = File(rootDir,"local.properties")
    if(localPropertiesFile.exists() && localPropertiesFile.isFile){
        localPropertiesFile.inputStream().use {
            localProperties.load(it)
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String","PUBLIC_KEY",localProperties.getProperty("PUBLIC_KEY"))
            buildConfigField("String","SECRECT_KEY",localProperties.getProperty("SECRECT_KEY"))
        }
        debug {
            buildConfigField("String","PUBLIC_KEY",localProperties.getProperty("PUBLIC_KEY"))
            buildConfigField("String","SECRECT_KEY",localProperties.getProperty("SECRECT_KEY"))
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
        buildConfig=true
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
    implementation(libs.firebase.auth)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //datastore
    implementation(libs.androidx.datastore.preferences)

    //Retrofit
    implementation (libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    //Room
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    //Dagger Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt (libs.hilt.compiler)

    //navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    //coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    //lottie jetpack compose
    implementation(libs.lottie.compose)

    //google maps
    implementation (libs.maps.compose)
    //play service location
    implementation(libs.gms.play.services.location)
    //play service maps
    implementation(libs.play.services.maps)

    //splash screen
    implementation(libs.androidx.core.splashscreen)

    //sweetalert dialog
//    implementation(libs.library)

    //material3 icon extended
    implementation(libs.androidx.material.icons.extended.android)

    //paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)
    implementation(libs.stripe.android)
    implementation (libs.stripe.java)




}