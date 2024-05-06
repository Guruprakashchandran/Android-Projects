// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.google.dagger.hilt.android") version "2.49" apply false
    id("app.cash.sqldelight") version "2.0.1" apply false
}

allprojects{
    repositories{
        maven(url = uri("https://oss.sonatype.org/content/repositories/snapshots/"))
    }
}