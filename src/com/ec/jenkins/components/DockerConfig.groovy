package com.ec.jenkins.components

class DockerConfig {

    def yaml
    DockerConfig ( def yaml ){
        this.yaml = yaml
    }

    String tenancyNamespace

    @NonCPS
    def imageName() {    
         if (!this.yaml || !this.yaml['dockerImageName']) {
            return "Dockerfile";
         }
        return yaml["dockerImageName"]
    }
}
