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
                        if(args.workDir==null){
                             mavenExecuteCommand('clean package',"${args.skipTest}".toBoolean())
                        } else {
                            dir(args.workDir) {
                                mavenExecuteCommand('clean package',"${args.skipTest}".toBoolean())
                            }
                        }
                    }
                }
            }

            stage("SonarQube Analysis") {
                steps {
                    script {
                        if(args.workDir==null){
                            sonarQubeAnalysis('sonar:sonar')
                        } else {
                            dir(args.workDir) {
                                sonarQubeAnalysis('sonar:sonar')
                            }
                        }
                    }
                }
            }

            stage("Build Docker Image") {
                steps {
                    script {
                        if(args.workDir==null){
                            Map pipelineCfg = readYaml(file: "${WORKSPACE}/pipeline.yaml")
                            pipelinePath =  "${WORKSPACE}"
                            echo pipelinePath
                            dockerBuild(pipelinePath)
                        } else {
                            dir(args.workDir) {
                                Map pipelineCfg = readYaml(file: "${WORKSPACE}/${args.workDir}/pipeline.yaml")
                                pipelinePath =  "${WORKSPACE}/${args.workDir}"
                                echo pipelinePath
                                dockerBuild(pipelinePath)   
                            }
                        }
                    }
                }
            }
			
		}
		
	}

}