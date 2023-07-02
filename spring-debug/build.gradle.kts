plugins {
    id("java")
}

group = "org.springframework"
version = "5.2.9.RELEASE"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
	compile(project(":spring-core"))
	optional(project(":spring-aop"))
	optional(project(":spring-beans"))
	optional(project(":spring-context"))
}

tasks.test {
    useJUnitPlatform()
}