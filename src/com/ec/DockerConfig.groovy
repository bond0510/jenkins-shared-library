package com.ec

class DockerConfig {

    def yaml
    DockerConfig ( def yaml ){
        this.yaml = yaml
    }
    
    def imageName() {    
         if (!this.yaml || !this.yaml["dockerImageName"]) {
            return "Dockerfile";
        }
        return config["dockerImageName"]
    }
}
