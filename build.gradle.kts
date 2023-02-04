buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.android.tools)
        classpath(libs.kotlin)
        classpath(libs.androidx.navigation.plugin)
        classpath(libs.dagger.hilt.plugin)
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}