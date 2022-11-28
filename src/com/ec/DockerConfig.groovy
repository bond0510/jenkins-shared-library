package com.ec

class DockerConfig {

    def yaml
    DockerConfig ( def yaml ){
        this.yaml = yaml
    }
    def imageName() {
        echo "${yaml.dockerConfig.dockerImageName}"
        return "${yaml.dockerConfig.dockerImageName}".toLowerCase()
    }
}
