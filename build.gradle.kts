plugins {
    `java-library`
    war
    `maven-publish`
}

group = "org.codelibs"
version = "2.1.38-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
tasks.withType<Test> {
    defaultCharacterEncoding = "UTF-8"
}
tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

dependencies {
    providedCompile("javax.servlet:javax.servlet-api:4.0.1")
    providedCompile("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.slf4j:slf4j-api:1.7.36")
    testImplementation("org.slf4j:slf4j-reload4j:1.7.36")
    implementation("org.bouncycastle:bcprov-jdk18on:1.76")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:1.10.19")
}

publishing {
    publications {
        create<MavenPublication>("jcifs") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            val snapshotsRepoUrl = project.property("enterpriseRepositorySnapshots").toString()
            val releasesRepoUrl = project.property("enterpriseRepositoryReleases").toString()
            url = uri(if (project.version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
            credentials {
                username = project.findProperty("enterpriseRepositoryUser")?.toString()
                password = project.findProperty("enterpriseRepositoryPassword")?.toString()
            }
        }
    }
}

tasks.wrapper {
   distributionType = Wrapper.DistributionType.BIN
   gradleVersion = "6.9.4"
}
