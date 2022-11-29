package com.ec.jenkins.components.services

def executeCommand(String command, boolean isSkipTest, Closure body=null) {
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

def sonarQubeAnalysis (Closure body=null) {

    withEnv([ 'JAVA_HOME=/usr/java/jdk' ]) {
            withSonarQubeEnv('Sonarqube web server') {
                sh './mvnw sonar:sonar '
            }
    }
}

