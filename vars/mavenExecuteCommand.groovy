def call ( String command, boolean isSkipTest, Closure body=null) {
    echo command
    withEnv(['JAVA_HOME=/usr/java/jdk']) {
                sh 'pwd'
                sh 'ls -lart'
                sh 'chmod 755 mvnw'
		        sh 'sed -i -e "s/\r$//" mvnw'
                sh 'ls -lart'
                if (isSkipTest){
                    sh "./mvnw ${command} -Dmaven.test.skip=true"
                } else{
                    sh "./mvnw ${command}"
                }
    }
}