plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    //plugins
    alias(libs.plugins.jetbrainsKotlinSerialization)
}

android {
    namespace = "com.yxhelzvl.umsnh_id"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.yxhelzvl.umsnh_id"
        minSdk = 24
        targetSdk = 35
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
    //Dependencias
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.material.icons.extended)
    //ktor
    /*implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)
    //koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)*/

    //ktor
    implementation(libs.bundles.ktor)
    //koin
    implementation(libs.bundles.koin)
    //mlkit
    implementation(libs.mlkit.barcode)
    //Crear Qr
    implementation(libs.google.zxing)
    //cameraX
    implementation(libs.bundles.camera)
    //Permisos
    implementation(libs.accompanist.permissions)
    //Persistencia de datos
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    implementation ("com.github.albfernandez:juniversalchardet:2.5.0")
}