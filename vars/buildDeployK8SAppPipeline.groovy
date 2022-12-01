
import java.lang.String
import java.util.Map
import com.ec.jenkins.components.parser.ConfigParser
import com.ec.jenkins.components.*
import com.ec.jenkins.components.services.*

def call(Map args=[:], Closure body=null ) {

	 pipeline {
        agent any

		environment {
            BRANCH_NAME = "${BRANCH_NAME}"
            WORKSPACE = "${WORKSPACE}"
            BUILD_NUMBER = "${BUILD_NUMBER}"
        }

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
                             new Maven().executeCommand('clean package',"${args.skipTest}".toBoolean())
                        } else {
                            dir(args.workDir) {
                                new Maven().executeCommand('clean package',"${args.skipTest}".toBoolean())
                            }
                        }
                    }
                }
            }

            stage("SonarQube Analysis") {
                steps {
                    script {
                        if(args.workDir==null){
                            new Maven().sonarQubeAnalysis()
                        } else {
                            dir(args.workDir) {
                                new Maven().sonarQubeAnalysis()
                            }
                        }
                    }
                }
            }

            stage("Build Docker Image") {
                steps {
                    script {
                        dockerBuild("${args.env}", "${args.workDir}")
                    }
                }
            }

            stage("Process Properties") {
                steps {
                    script {
                        processProperties()
                    }
                }
            }
			
		}
		
	}

}