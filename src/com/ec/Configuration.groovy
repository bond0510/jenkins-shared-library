package com.ec

import com.ec.parser.*

class Configuration {

    ProjectConfiguration projectConfig

    public static DOCKER_REGISTRY_URL = 'https://fra.ocir.io'

    public static DOCKER_REGISTRY_CREDENTIAL_ID = 'oicr_creds'

    public static FULL_IMAGE_REPO_URL = "fra.ocir.io/entercard/msp/${env.env}/"

    Configuration (def pipelineCfg , String env ) {
        this.projectConfig = ConfigParser.parse(pipelineCfg)
        env.env=env
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
