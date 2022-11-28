class Configuration {

    ProjectConfiguration projectConfig

    //public static FULL_IMAGE_REPO_URL = "fra.ocir.io/entercard/msp/${env}/"

    Configuration (  ) {
        Map pipelineCfg = readYaml(file: "${WORKSPACE}/pipeline.yaml")

        this.projectConfig = ConfigParser.parse(pipelineCfg)
    }

    def dockerConfig() {

        echo this.projectConfig?.dockerConfig

    }

}
