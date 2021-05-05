import dependencies.AnnotationProcessorsDependencies
import dependencies.Dependencies
import extensions.buildConfigStringField
import extensions.implementation
import extensions.kapt

plugins {
    id("commons.android-library")
}

allOpen {
    // allows mocking for classes w/o directly opening them for release builds
    annotation("com.koidev.core.annotations.OpenClass")
}

android {
    buildTypes.forEach {
        it.buildConfigStringField("TRADERNET_API_BASE_URL", "https://wss.tradernet.ru/")
    }
}

dependencies {
    implementation(Dependencies.ROOM)
    implementation(Dependencies.ROOM_KTX)
    implementation(Dependencies.LIFECYCLE_EXTENSIONS)
    implementation(Dependencies.NAVIGATION_UI)
    implementation(Dependencies.FRAGMENT_KTX)
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_CONVERTER)
    implementation(Dependencies.LOGGING)
    implementation(Dependencies.MOSHI)
    implementation(Dependencies.MOSHI_KTX)

    kapt(AnnotationProcessorsDependencies.DATABINDING)
    kapt(AnnotationProcessorsDependencies.ROOM)

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    api("androidx.paging:paging-common-ktx:3.0.0-rc01")
//    kotlin-coroutines-android = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-android", version.ref = "coroutines" }
//    kotlin-coroutines-core = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-core", version.ref = "coroutines" }
//    kotlin-coroutines-test = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-test", version.ref = "coroutines" }
}

