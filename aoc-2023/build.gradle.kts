plugins {
    kotlin("jvm") version "1.9.20"
}

repositories {
    mavenCentral()
}

dependencies {
    // Multik
    implementation("org.jetbrains.kotlinx:multik-core:0.2.2")
    implementation("org.jetbrains.kotlinx:multik-default:0.2.2")

    testImplementation(kotlin("test"))
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

kotlin {
    jvmToolchain(8)
}
