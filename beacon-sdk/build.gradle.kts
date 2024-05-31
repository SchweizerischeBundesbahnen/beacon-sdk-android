plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.9.0"
    id("maven-publish")
}

android {
    namespace = "ch.allianceswisspass.beaconsdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("org.altbeacon:android-beacon-library:2.20.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("io.ktor:ktor-client-core:2.3.5")
    implementation("io.ktor:ktor-client-cio:2.3.5")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.5")
    implementation("io.ktor:ktor-client-logging:2.3.5")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
    implementation("org.slf4j:slf4j-api:2.0.12")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.10.0")
    testImplementation("com.google.truth:truth:1.3.0")
    // Uncomment the next line to enable logging in tests
    // testImplementation("org.slf4j:slf4j-simple:2.0.12")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "ch.allianceswisspass.beaconsdk"
            artifactId = "beacon-sdk"
            version = "0.4.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
