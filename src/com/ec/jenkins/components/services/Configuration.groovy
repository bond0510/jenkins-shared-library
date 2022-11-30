package com.ec.jenkins.components.services

import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.parser.ConfigParser

class Configuration {

    ProjectConfiguration projectConfig

    String env

    public static DOCKER_REGISTRY_URL = 'https://fra.ocir.io'

    public static DOCKER_REGISTRY_CREDENTIAL_ID = 'oicr_creds'

    public static FULL_IMAGE_REPO_URL = "fra.ocir.io/entercard/msp/"

    Configuration (def pipelineCfg , String env ) {
        this.projectConfig = ConfigParser.parse(pipelineCfg)
        this.env = env
    // steps.env.repoEnv = steps.sh(script: 'echo $env', returnStdout: true).trim()
    }

    @NonCPS
    def getDockerConfig() {
        return this.projectConfig.dockerConfig
    }

    @NonCPS
    def imageName() {
        return this.projectConfig.dockerConfig.imageName()
    }

    def tenancyNamespace () {
        switch (env) {
            case 'dev':
                return "${FULL_IMAGE_REPO_URL}" + '/tstenv/'
                break
            case 'test':
                /* groovylint-disable-next-line DuplicateStringLiteral */
                return "${FULL_IMAGE_REPO_URL}"+'/tstenv/'
                break
            case 'stage':
                return "${FULL_IMAGE_REPO_URL}" + '/stgenv/'
                break
            case 'prod' :
                /* groovylint-disable-next-line ConsecutiveStringConcatenation */
                return "${FULL_IMAGE_REPO_URL}" + '/prdenv/'
                break
            default:
                return "${FULL_IMAGE_REPO_URL}" + '/tstenv/'
                break
        }
    }

}
