package com.ec

class DockerConfig {

    def yaml
    DockerConfig ( def yaml){
        this.yaml=yaml
    }
    def imageName() {
        "${yaml.dockerConfig.dockerImageName}".toLowerCase()
    }
}
