package com.ec.jenkins.components.services

import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.parser.ConfigParser

class Configuration {

    ProjectConfiguration projectConfig

    def steps

    public static DOCKER_REGISTRY_URL = 'https://fra.ocir.io'

    public static DOCKER_REGISTRY_CREDENTIAL_ID = 'oicr_creds'

    public static FULL_IMAGE_REPO_URL = "fra.ocir.io/entercard/msp/${env.repoName}/"

    Configuration ( steps, def pipelineCfg , String env ) {
        this.steps = steps
        this.projectConfig = ConfigParser.parse(pipelineCfg)
        env.repoName = steps.sh(script: 'echo $env', returnStdout: true).trim()
    }

    @NonCPS
    def getDockerConfig() {
        return this.projectConfig.dockerConfig
    }

    @NonCPS
    def imageName() {
        return this.projectConfig.dockerConfig.imageName()
    }


}
