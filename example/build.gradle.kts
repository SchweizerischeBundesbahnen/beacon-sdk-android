import com.android.build.api.dsl.VariantDimension
import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}



android {
    namespace = "ch.allianceswisspass.beaconsdk.example"
    compileSdk = 34

    defaultConfig {
        applicationId = "ch.allianceswisspass.beaconsdk.example"
        minSdk = 23
        targetSdk = 34
        versionCode = 3
        versionName = "0.3.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    flavorDimensions.add("asp")
    productFlavors {
        create("dev") {
            dimension = "asp"
            applicationIdSuffix = ".dev"
            setBeaconSDKBuildConfigFields(
                vd = this,
                propertiesFile = File(rootProject.rootDir, "example/beacon_sdk_dev.properties")
            )
        }

        create("prod") {
            dimension = "asp"
            setBeaconSDKBuildConfigFields(
                vd = this,
                propertiesFile = File(rootProject.rootDir, "example/beacon_sdk_prod.properties")
            )
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(path = ":beacon-sdk"))

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui:1.6.5")
    implementation("androidx.compose.ui:ui-graphics:1.6.5")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.5")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.5")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")
    implementation("com.google.dagger:hilt-android:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    ksp("com.google.dagger:hilt-android-compiler:2.50")
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("io.github.unveloper:slf4j-timber:0.0.8")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

fun setBeaconSDKBuildConfigFields(vd: VariantDimension, propertiesFile: File) {
    val properties = Properties().apply {
        val file = when {
            propertiesFile.exists() -> propertiesFile
            else -> File(rootProject.rootDir, "example/beacon_sdk.properties")
        }
        val fis = FileInputStream(file)
        load(fis)
    }
    val clientId = properties.getProperty("clientId")
    vd.buildConfigField("String", "BEACON_SDK_CLIENT_ID", clientId)
    val clientSecret = properties.getProperty("clientSecret")
    vd.buildConfigField("String", "BEACON_SDK_CLIENT_SECRET", clientSecret)
    val scope = properties.getProperty("scope")
    vd.buildConfigField("String", "BEACON_SDK_SCOPE", scope)
    val useCaseApiBaseUrl = properties.getProperty("useCaseApiBaseUrl")
    vd.buildConfigField("String", "BEACON_SDK_USE_CASE_API_BASE_URL", useCaseApiBaseUrl)
}
