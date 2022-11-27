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
                        echo repoUrl
                    }
                }
            }
			
		}
		
	}

}

  node {
        stage('Code Checkout') { // for display purposes
            println "REPO : ${args.repo}"
            checkout poll: true, scm: [$class: 'GitSCM', branches: [[name: 'refs/heads/test']], doGenerateSubmoduleConfigurations: true, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'Bitbucket_SSHkey', url: "${args.repo}"]]]
        }
        def yaml = pipelineConfig()
				
        stage('Process Properties') {
			// load project's configuration
    		ProjectConfiguration projectConfig = ConfigParser.parse(yaml);
            List<Property> stepsA = projectConfig.properties.props
			stepsA.each { step ->
                stage(step.name) {
                    step.commands.each { command ->
                        echo "${command}"
						def key="TEST_${command}"
                        def variableName="${command}"
						 script {
							env.fileName="${step.name}"
							env.placeHolder="${command}_VAL"
						}
						withCredentials([string(credentialsId: key, variable: 'VALUE')]) {
						    dir('common'){
								echo " >> ${env.fileName}"
								echo ">>> ${env.placeHolder}"
                                sh'''
                                echo $placeHolder  $fileName
								'''
						    }
                        }
				}
            }
        }
        }
          
        
    }

 
}
