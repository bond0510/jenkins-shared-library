package com.ec.jenkins.components

class DockerConfig {

    def yaml
    DockerConfig ( def yaml ){
        this.yaml = yaml
    }

    String tenancyNamespace
    String imageName

}
