// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false

    alias(libs.plugins.google.dagger.hilt.android) apply false

    id("com.google.devtools.ksp") version "2.0.0-1.0.24" apply false
}

allprojects {
    configurations.all {
        // Exclude the old JetBrains annotations
        exclude(group = "com.intellij", module = "annotations")

    }
}
