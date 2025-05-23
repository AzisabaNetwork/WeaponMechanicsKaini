plugins {
    `maven-publish`
    signing
    id("io.codearte.nexus-staging") version "0.30.0"
    id("me.deecaad.mechanics-project")
    kotlin("jvm") version Versions.KOTLIN
}

repositories {
    mavenCentral()
    maven(url = "https://mvn.lumine.io/repository/maven-public/")
    maven(url = "https://repo.jeff-media.com/public/")
}

dependencies {

    // Core Bukkit/Minecraft libs
    compileOnly(Dependencies.LATEST_SPIGOT_API)

    // External plugins that we hook into
    compileOnly(project(":MechanicsCore"))
    compileOnly(Dependencies.PACKET_EVENTS)
    compileOnly(Dependencies.PLACEHOLDER_API)
    compileOnly(Dependencies.MYTHIC_MOBS)
    compileOnly(Dependencies.VIVECRAFT)

    // Misc libs
    compileOnly(Dependencies.BSTATS)
    compileOnly("com.jeff_media:SpigotUpdateChecker:3.0.3")
    compileOnly(Dependencies.GSON)
    adventureChatAPI()
    compileOnly(Dependencies.FAST_UTIL)
    compileOnly(Dependencies.FOLIA_SCHEDULER)
    compileOnly(Dependencies.COMMAND_API)
    compileOnly(Dependencies.COMMAND_API_KOTLIN)
}

// Create javadocJar and sourcesJar tasks
val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.named("javadoc"))
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "21"
    }
}

nexusStaging {
    serverUrl = "https://s01.oss.sonatype.org/service/local/"
    packageGroup = "com.cjcrafter"
    stagingProfileId = findProperty("OSSRH_ID").toString()
    username = findProperty("OSSRH_USERNAME").toString()
    password = findProperty("OSSRH_PASSWORD").toString()
    numberOfRetries = 30
    delayBetweenRetriesInMillis = 3000
}

// Signing artifacts
signing {
    isRequired = true
    //useGpgCmd()

    useInMemoryPgpKeys(
        findProperty("SIGNING_KEY_ID").toString(),
        findProperty("SIGNING_PRIVATE_KEY").toString(),
        findProperty("SIGNING_PASSWORD").toString()
    )
    //sign(configurations["archives"])
    sign(publishing.publications)
}

//publishing {
//    publications {
//        create<MavenPublication>("mavenJava") {
//            from(components["java"])
//
//            artifact(javadocJar)
//            artifact(sourcesJar)
//
//            pom {
//                name.set("WeaponMechanicsKaini")
//                description.set("A new age of weapons in Minecraft")
//                url.set("https://github.com/WeaponMechanics/MechanicsMain")
//
//                groupId = "com.cjcrafter"
//                artifactId = "weaponmechanicsKaini"
//                version = findProperty("weaponMechanicsVersion") as? String ?: throw IllegalArgumentException("property was null")
//
//                licenses {
//                    license {
//                        name.set("The MIT License")
//                        url.set("https://opensource.org/licenses/MIT")
//                    }
//                }
//                developers {
//                    developer {
//                        id.set("CJCrafter")
//                        name.set("Collin Barber")
//                        email.set("collinjbarber@gmail.com")
//                    }
//                    developer {
//                        id.set("DeeCaaD")
//                        name.set("DeeCaaD")
//                        email.set("perttu.kangas@hotmail.fi")
//                    }
//                    developer {
//                        id.set("miyabi0333")
//                        name.set("miyabi0333")
//                    }
//                }
//                scm {
//                    connection.set("scm:git:https://github.com/WeaponMechanics/MechanicsMain.git")
//                    developerConnection.set("scm:git:ssh://github.com/WeaponMechanics/MechanicsMain.git")
//                    url.set("https://github.com/WeaponMechanics/MechanicsMain")
//                }
//            }
//        }
//    }
//
//    repositories {
//        maven {
//            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2")
//            credentials {
//                username = findProperty("OSSRH_USERNAME").toString()
//                password = findProperty("OSSRH_PASSWORD").toString()
//            }
//        }
//    }
//}

// After publishing, the nexus plugin will automatically close and release
tasks.named("publish") {
    finalizedBy("closeAndReleaseRepository")
}
