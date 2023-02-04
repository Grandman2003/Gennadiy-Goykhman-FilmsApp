import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.filmsapp"
    compileSdk = 33

    defaultConfig {
        applicationId  = "com.example.filmsapp"
        minSdk  = 26
        targetSdk  = 33
        versionCode  = 1
        versionName  = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables{
            useSupportLibrary = true
        }
        val properties: Properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField("String", "KINOPOISK_API_KEY", "\"${properties.getProperty("KINOPOISK_API_KEY")}\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions{
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    compileOptions {
        sourceCompatibility  = JavaVersion.VERSION_1_8
        targetCompatibility  = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}

dependencies {

    implementation ("androidx.core:core-ktx:1.8.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation ("androidx.activity:activity-compose:1.3.1")
    implementation ("androidx.compose.material:material:1.1.1")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.4")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.0")

    //Jetpack Compose
    implementation (libs.compose.ui.ui)
    implementation (libs.compose.ui.tooling.preview)
    androidTestImplementation (libs.compose.ui.test)
    debugImplementation (libs.compose.ui.tooling)
    debugImplementation (libs.compose.ui.test.manifest)
    implementation(libs.compose.ui.accomponist)
    implementation(libs.compose.ui.accomponist.permissions)
    implementation(libs.compose.ui.accomponist.drawablepainter)
    implementation(libs.compose.ui.navigation)
    implementation(libs.compose.ui.material)


    // Material Design
    implementation(libs.android.material)

    // Architectural Components
    implementation(libs.androidx.lifecycle.viewmodel)

    // Room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compile)

    // Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)

    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Coroutine Lifecycle Scopes
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.runtime)

    // Navigation Components
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // Glide
    implementation(libs.glide.core)
    kapt(libs.glide.compiler)

    // Google Maps Location Services
    implementation(libs.gms.location)
    //implementation(libs.gms.maps)

    //Yandex MapKit
    implementation(libs.yandex.maps)

    // Dagger Core
    implementation(libs.dagger.core)
    kapt(libs.dagger.compiler)

    // Dagger Android
    api(libs.dagger.android.core)
    api(libs.dagger.android.support)
    kapt(libs.dagger.android.processor)

    //Activity KTX for viewModels()
    implementation(libs.androidx.activity.ktx)

    //Dagger Hilt
    implementation(libs.dagger.hilt.core)
    kapt(libs.dagger.hilt.compiler)

    //Dagger Hilt Androidx
    kapt(libs.dagger.hilt.androidx.compiler)

    // Easy Permissions
    //implementation(libs.easypermissions)

    // Timber
    implementation(libs.timber)

    //Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp.logging)

    //Google Gson
    implementation(libs.gson)

    //Android Security
    implementation(libs.androidx.crypto)

    implementation(libs.android.arch.lifecycle)
}