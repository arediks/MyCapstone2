plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.mygithub.core"
    compileSdk = 34

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "HOST_NAME", "\"api.github.com\"")
        buildConfigField("String", "CERT_1", "\"sha256/GyhWVHsOXNZc6tGTNd15kXF9YD0kEZaGxYn6MUva5jY=\"")
        buildConfigField("String", "CERT_2", "\"sha256/lmo8/KPXoMsxI+J9hY+ibNm2r0IYChmOsF9BxD74PVc=\"")
        buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
        buildConfigField("String", "API_KEY", "\"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\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = true
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
    api("androidx.core:core-ktx:1.13.1")
    api("androidx.appcompat:appcompat:1.7.0")
    api("com.google.android.material:material:1.12.0")
    api("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // ui
    api("de.hdodenhof:circleimageview:3.1.0")
    api("com.github.bumptech.glide:glide:4.16.0")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // coroutine support
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    api("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")
    implementation("androidx.room:room-ktx:2.6.1")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // room
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // by viewModels in activity and fragment
    api("androidx.activity:activity-ktx:1.9.2")
    api("androidx.fragment:fragment-ktx:1.8.3")

    // splash screen
    api("androidx.core:core-splashscreen:1.0.1")

    // dagger hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    ksp("com.google.dagger:hilt-android-compiler:2.51.1")

    // paging 3
    api("androidx.paging:paging-runtime-ktx:3.3.2")

    // room with paging support
    implementation("androidx.room:room-paging:2.6.1")

    // flow binding
    api("io.github.reactivecircus.flowbinding:flowbinding-android:1.2.0")

    // debug leakcanary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.14")

    // encryption datastore
    implementation("io.github.osipxd:security-crypto-datastore-preferences:1.1.1-beta03")

    // encryption database
    implementation("net.zetetic:android-database-sqlcipher:4.4.0")
}