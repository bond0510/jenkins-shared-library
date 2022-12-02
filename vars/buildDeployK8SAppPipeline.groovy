import java.lang.String
import java.util.Map
import com.ec.jenkins.components.parser.ConfigParser
import com.ec.jenkins.components.*
import com.ec.jenkins.components.services.*

def call(Map args=[:] ) {
    config = Utils.parseConfig(args)

    pipeline {
        agent any

        environment {
            BRANCH_NAME = "${BRANCH_NAME}"
            WORKSPACE = "${WORKSPACE}"
            BUILD_NUMBER = "${BUILD_NUMBER}"
        }

        stages {
            stage('Checkout Source Code') {
                steps {
                    script {
                        repoUrl = "${args.repo}"
                        env = "${args.env}"
                        checkoutSourceCode(repoUrl, env)
                    }
                }
            }
            stage('Process Pipeline Config') {
                steps {
                    script {
                        config = pipelineConfig( config )
                    }
                }
            }
            stage('Build Code') {
                steps {
                    script {
                        if (args.workDir == null) {
                            TAG_VERSION = readMavenPom().getVersion()
                            config.put ( 'TAG_VERSION' , TAG_VERSION)
                            new Maven().executeCommand('clean package', "${args.skipTest}".toBoolean())
                        } else {
                            dir(args.workDir) {
                                TAG_VERSION = readMavenPom().getVersion()
                                config.put ( 'TAG_VERSION' , TAG_VERSION)
                                new Maven().executeCommand('clean package', "${args.skipTest}".toBoolean())
                            }
                        }
                    }
                }
            }

            stage('SonarQube Analysis') {
                steps {
                    script {
                        if (args.workDir == null) {
                            new Maven().sonarQubeAnalysis()
                        } else {
                            dir(args.workDir) {
                                new Maven().sonarQubeAnalysis()
                            }
                        }
                    }
                }
            }

            stage('Build Docker Image') {
                steps {
                    script {
                        dockerBuild( config )
                    }
                }
            }

            stage('Process Properties') {
                steps {
                    script {
                        processProperties( config )
                    }
                }
            }

            stage('Process Secretes') {
                steps {
                    script {
                        processProperties( config )
                    }
                }
            }

            stage('Deploy on Kubernetes') {
                steps {
                    script {
                        processProperties( config )
                    }
                }
            }

            stage('Container Status') {
                steps {
                    script {
                        processProperties( config )
                    }
                }
            }
        }
    }
}
