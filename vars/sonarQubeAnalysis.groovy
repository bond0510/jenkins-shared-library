def call ( String command, Closure body=null) {
    echo command
     withEnv(['JAVA_HOME=/usr/java/jdk']) {
            withSonarQubeEnv('Sonarqube web server') {
                sh "./mvnw ${command}"
        }
     }
}