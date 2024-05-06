plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("app.cash.sqldelight")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {

        applicationId = "com.example.myapplication"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions{
        kotlinCompilerExtensionVersion = "1.0.4"
//        kotlinCompilerVersion = "1.5.30"
    }
    dataBinding{
        enable = true
    }
}

dependencies {

    val sqlDelightVersion = "2.0.1"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
//    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
//    implementation("androidx.compose.material3:material3")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.compose.ui:ui-android:1.6.4")
    implementation ("androidx.compose.ui:ui:1.6.4")
    implementation ("androidx.compose.runtime:runtime:1.6.4")
    implementation ("androidx.compose.foundation:foundation:1.6.4")
    implementation ("androidx.compose.animation:animation:1.6.4")
    implementation ("androidx.compose.material3:material3:1.2.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    implementation("app.cash.sqldelight:android-driver:$sqlDelightVersion")
    implementation("app.cash.sqldelight:coroutines-extensions-jvm:$sqlDelightVersion")

//    implementation("com.squareup.sqldelight:android-driver:1.5.5")
//    implementation("com.squareup.sqldelight:coroutines-extensions:1.5.5")

    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-android-compiler:2.49")
    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")
    implementation("androidx.hilt:hilt-work:1.2.0")

    implementation ("androidx.work:work-runtime-ktx:2.9.0")

    implementation( "com.google.android.material:material:1.11.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    implementation("it.xabaras.android:recyclerview-swipedecorator:1.4")
//    implementation("androidx.compose.material3:material3:1.2.0")
//    implementation("androidx.compose.material3:material3-window-size-class:1.2.0")
//    implementation("androidx.compose.material3:material3-adaptive:1.0.0-alpha06")
//    implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.0.0-alpha04")

    // When using Kotlin.
//    kapt("androidx.hilt:hilt-compiler:1.2.0")
//    // When using Java.
//    annotationProcessor("androidx.hilt:hilt-compiler:1.2.0")
}

sqldelight{
    databases{
        create("Database"){
            packageName.set("com.example.myapplication2")
        }
    }
}
kapt {
    correctErrorTypes = true
}