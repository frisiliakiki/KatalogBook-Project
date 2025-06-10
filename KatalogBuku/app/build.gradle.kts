plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.katalogbuku"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.katalogbuku"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Pustaka untuk melakukan request ke API (sesuai permintaan Anda)
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

// Pustaka untuk mengubah data JSON dari API menjadi objek Java
    implementation("com.google.code.gson:gson:2.10.1")

// Pustaka untuk memuat gambar dari URL dengan mudah
    implementation("com.github.bumptech.glide:glide:4.16.0")

// Pustaka untuk membuat tampilan list item lebih menarik
    implementation("androidx.cardview:cardview:1.0.0")

// Pustaka untuk Navigasi antar Fragment
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")

// Pustaka Inti (beberapa mungkin sudah ada)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Room Database (untuk caching data)
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
}