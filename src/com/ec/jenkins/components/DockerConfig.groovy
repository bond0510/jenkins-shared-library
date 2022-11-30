package com.ec.jenkins.components

class DockerConfig {

    def yaml

    public static FULL_IMAGE_REPO_URL = "fra.ocir.io/entercard/msp/"

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

    def tenancyNamespace (String env ) {
         switch (env) {
            case ['dev' ,'test']:
                return "${FULL_IMAGE_REPO_URL}" + 'tstenv/'
                break
            case 'stage':
                return "${FULL_IMAGE_REPO_URL}" + 'stgenv/'
                break
            case 'prod' :
                /* groovylint-disable-next-line ConsecutiveStringConcatenation */
                return "${FULL_IMAGE_REPO_URL}" + 'prdenv/'
                break
            default:
                return "${FULL_IMAGE_REPO_URL}" + 'tstenv/'
                break
        }
    }
}
