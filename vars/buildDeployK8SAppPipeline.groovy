import java.lang.String
import java.util.Map
import com.ec.jenkins.components.parser.ConfigParser
import com.ec.jenkins.components.*
import com.ec.jenkins.components.services.*

def call(Map args=[:] ) {
    config = Utils.parseConfig(args)

    pipeline {
        agent any

        stages {
            stage('Checkout Source Code') {
                steps {
                    script {
                        repoUrl = "${args.repo}"
                        env = "${args.env}"
                        checkoutSourceCode(config)
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
                        dir(args.workingDirectory) {
                            TAG_VERSION = readMavenPom().getVersion()
                            config.put ( 'TAG_VERSION' , TAG_VERSION)
                            new Maven().executeCommand('clean package', "${args.skipTest}".toBoolean())
                        }
                    }
                }
            }

            stage('SonarQube Analysis') {
                steps {
                    script {
                        dir(args.workingDirectory) {
                            new Maven().sonarQubeAnalysis()
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
            stage('Flyway Execution') {
                when {
                    expression { return args.flywayExecution ==~ /(?i)(Y|YES|T|TRUE|ON|RUN)/ }
                }
                steps {
                    script {
                        dir(args.workingDirectory) {
                            flywayExecution ( config )
                        }
                    }
                }
            }
            stage('Process Properties') {
                steps {
                    script {
                        dir(args.workingDirectory) {
                            processProperties( config )
                        }
                    }
                }
            }

            stage('Process Secretes') {
                steps {
                    script {
                        processSecretes( config )
                    }
                }
            }

            stage('Process Credentials') {
                steps {
                    script {
                        processCredentials( config )
                    }
                }
            }

            stage('Deploy on Kubernetes') {
                steps {
                    script {
                        kubernetesExecution( config )
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
