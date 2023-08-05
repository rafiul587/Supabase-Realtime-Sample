plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.example.supabaserealtime"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.supabaserealtime"
        minSdk = 24
        targetSdk = 33
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.6.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation (platform("io.github.jan-tennert.supabase:bom:1.2.0-alpha-2"))
    implementation ("io.github.jan-tennert.supabase:gotrue-kt")
    implementation ("io.github.jan-tennert.supabase:postgrest-kt")
    implementation ("io.github.jan-tennert.supabase:realtime-kt")
    implementation ("io.github.jan-tennert.supabase:storage-kt")
    val ktor_version = "2.3.2"
    implementation ("io.ktor:ktor-client-cio:$ktor_version")
    implementation ("io.ktor:ktor-client-core:$ktor_version")
    implementation ("io.ktor:ktor-utils:$ktor_version")
}