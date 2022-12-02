package com.ec.jenkins.components.services

void executeCommand(String command, boolean isSkipTest) {
    echo command
    withEnv([ 'JAVA_HOME=/usr/java/jdk' ]) {
                sh 'pwd'
                sh 'ls -lart'
                sh 'chmod 755 mvnw'
                sh 'sed -i -e "s/\r$//" mvnw'
                if (isSkipTest) {
            sh "./mvnw ${command} -Dmaven.test.skip=true"
                } else {
            sh "./mvnw ${command}"
                }
    }
}

void sonarQubeAnalysis ( ) {
    withEnv([ 'JAVA_HOME=/usr/java/jdk' ]) {
            withSonarQubeEnv('Sonarqube web server') {
                sh './mvnw sonar:sonar '
            }
    }
}

