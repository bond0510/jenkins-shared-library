package com.ec

class DockerConfig {

    def yaml
    DockerConfig ( def yaml ){
        this.yaml = yaml
    }

    @NonCPS
    def imageName() {    
         if (!this.yaml || !this.yaml['dockerImageName']) {
            return "Dockerfile";
         }
        return yaml["dockerImageName"]
    }
}
