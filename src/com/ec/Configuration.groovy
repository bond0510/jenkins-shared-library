package com.ec

class Configuration {

    ProjectConfiguration projectConfig

    //public static FULL_IMAGE_REPO_URL = "fra.ocir.io/entercard/msp/${env}/"

    Configuration (def pipelinePath ) {
        echo pipelinePath
        if (fileExists("${pipelinePath}/pipeline.yaml")) {
            config = readYaml file: "${pipelinePath}/pipeline.yaml"
            this.projectConfig = ConfigParser.parse(config)
            println "config ==> ${config}"
        } else {
            config = []
        }
    }

    def dockerConfig() {
        echo this.projectConfig?.dockerConfig
    }

}
