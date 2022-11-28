package com.ec

class DockerConfig {

    def yaml
    DockerConfig ( def yaml){
        this.yaml = yaml
    }
    def imageName() {
        echo "${yaml.dockerConfig.dockerImageName}"
        "${yaml.dockerConfig.dockerImageName}".toLowerCase()
    }
}
