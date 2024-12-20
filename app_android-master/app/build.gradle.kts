plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.app_n1"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.app_n1"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.5") // chuyen navi
    implementation("androidx.navigation:navigation-ui-ktx:2.8.5") // chuyen navi
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0") // ve bieu do đường
    implementation("androidx.viewpager2:viewpager2:1.1.0") // tạo trang scroll
    implementation(platform("com.google.firebase:firebase-bom:33.5.0"))
    implementation("com.github.bumptech.glide:glide:4.16.0") // sử dụng hình ảnh trên internet
    implementation("com.google.firebase:firebase-analytics")
    kapt ("com.github.bumptech.glide:compiler:4.11.0")// sử dụng hình ảnh trên internet

}
