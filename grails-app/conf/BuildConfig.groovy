grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility
    repositories {
        grailsCentral()
        mavenCentral()
        mavenLocal()
    }
    dependencies {
        test 'org.gmock:gmock:0.8.0'
        test 'org.hamcrest:hamcrest-library:1.1' // Optionally, you can use hamcrest matchers
    }

    plugins {
        build(":tomcat:$grailsVersion",
              ":release:2.2.1") {
            export = false
        }
    }
}
