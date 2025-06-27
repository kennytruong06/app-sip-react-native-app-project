import java.util.Properties
import java.io.FileInputStream

repositories {
    google()
    mavenCentral()
}

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
    id("com.google.gms.google-services") version "4.4.0"
}

android {
    namespace = "org.telnyx.webrtc.compose_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.telnyx.webrtc.compose_app"
        minSdk = 24
        targetSdk = 34
        versionCode = 16
//        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        val properties = Properties()
        val localPropertiesFile = file("${rootDir}/local.properties")

        if (localPropertiesFile.exists()) {
            FileInputStream(localPropertiesFile).use { properties.load(it) }
        }

        buildConfigField("String", "TEST_SIP_USERNAME", "\"${properties.getProperty("TEST_SIP_USERNAME", "default_username")}\"")
        buildConfigField("String", "TEST_SIP_PASSWORD", "\"${properties.getProperty("TEST_SIP_PASSWORD", "default_password")}\"")
        buildConfigField("String", "TEST_SIP_CALLER_NAME", "\"${properties.getProperty("TEST_SIP_CALLER_NAME", "default_callername")}\"")
        buildConfigField("String", "TEST_SIP_CALLER_NUMBER", "\"${properties.getProperty("TEST_SIP_CALLER_NUMBER", "default_callernumber")}\"")
        buildConfigField("String", "TEST_SIP_DEST_NUMBER", "\"${properties.getProperty("TEST_SIP_DEST_NUMBER", "default_dest_number")}\"")
        buildConfigField("String", "PRECALL_DIAGNOSIS_NUMBER", "\"${properties.getProperty("PRECALL_DIAGNOSIS_NUMBER", "default_precall_number")}\"")
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "2.0.0"
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    publishing {
        singleVariant("debug") {
            withSourcesJar()
            withJavadocJar()
        }
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
    buildTypes {
        getByName("debug") {
            matchingFallbacks += listOf("debug")
        }
        getByName("release") {
            matchingFallbacks += listOf("release")
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
    implementation("androidx.navigation:navigation-compose:2.8.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.7")

    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("com.google.firebase:firebase-messaging-ktx:24.1.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.karumi:dexter:6.2.3")
    implementation(project(":telnyx_common"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.1")
}
