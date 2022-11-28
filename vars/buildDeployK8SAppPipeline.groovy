import java.lang.String;
import java.util.Map;
import com.ec.parser.ConfigParser;
import com.ec.*;

def call(Map args=[:]) {

	 pipeline {
        agent any
		
		stages {
		
			stage("Checkout Source Code") {
                steps {
                    script {
                        repoUrl = "${args.repo}"
                        env = "${args.env}"
                        checkoutSourceCode(repoUrl,env)
                    }
                }
            }
            stage("Build Code") {
                steps {
                    script {
                       command='clean package'
                       skipTest = "${args.skipTest}"
                       mavenExecuteCommand(command,skipTest)
                    }
                }
            }

            stage("SonarQube Analysis") {
                steps {
                    script {
                       command='sonar:sonar'
                       sonarQubeAnalysis(command)
                    }
                }
            }
			
		}
		
	}

}