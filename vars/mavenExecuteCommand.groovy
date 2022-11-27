def call ( String command, Closure body=null) {
    echo command
    withEnv(['JAVA_HOME=/usr/java/jdk']) {
                sh 'pwd'
                sh 'ls -lart'
                sh 'chmod 755 mvnw'
		        sh 'sed -i -e "s/\r$//" mvnw'
                sh 'ls -lart'
                sh './mvnw ${command}' 
    }
}