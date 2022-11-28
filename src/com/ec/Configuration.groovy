package com.ec

class Configuration {

    ProjectConfiguration projectConfig

    //public static FULL_IMAGE_REPO_URL = "fra.ocir.io/entercard/msp/${env}/"

    Configuration (String pipelinePath ) {
        Map pipelineCfg = readYaml(file: "${pipelinePath}" )

        this.projectConfig = ConfigParser.parse(pipelineCfg)
    }

    def dockerConfig() {
        echo this.projectConfig?.dockerConfig
    }

}
