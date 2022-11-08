import java.lang.String;
import java.util.Map;
import com.ec.parser.ConfigParser;
import com.ec.*;

def call(Map args=[:]) {

  node {
        stage('Code Checkout') { // for display purposes
            println "REPO : ${args.repo}"
            checkout poll: true, scm: [$class: 'GitSCM', branches: [[name: 'refs/heads/test']], doGenerateSubmoduleConfigurations: true, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'Bitbucket_SSHkey', url: "${args.repo}"]]]
        }
        def yaml = pipelineConfig()
        stage('Process Properties') {
			// load project's configuration
    		ProjectConfiguration projectConfig = ConfigParser.parse(yaml);
            echo "${projectConfig}"
            List<Property> stepsA = projectConfig.properties.props
			stepsA.each { step ->
                stage(step.name) {
                    step.commands.each { command ->
                        echo "${command}"
						def key="TEST_${command}"
                        def variableName="${command}"
						def fileName="${step.name}"
						def placeHolder="${command}_VAL"
						echo"${placeHolder}"
						withCredentials([string(credentialsId: key, variable: 'VALUE')]) {
						dir('common'){
                            sh 'echo $placeHolder'
							sh  'sed -i "s/"$placeHolder"/$VALUE/g" $fileName'
						}
                    }
				}
            }
        }
        }
          
        
    }

 
}
