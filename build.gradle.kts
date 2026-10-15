plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.25.3")
    implementation("io.rest-assured:rest-assured:5.5.6")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.27.0")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.9.2")
    testImplementation("com.codeborne:selenide:7.6.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.register<Test>("runApi") {
    group = "verification"

    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
    outputs.upToDateWhen { false }

    useJUnitPlatform {
        includeTags("api")
    }
    testLogging {
        showStandardStreams = true
        events("passed", "skipped", "failed")
    }
}