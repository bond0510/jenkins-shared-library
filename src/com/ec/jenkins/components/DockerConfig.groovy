package com.ec.jenkins.components

class DockerConfig {

    String imageName

    String ocirDockerImageName

    public static FULL_IMAGE_REPO_URL = "fra.ocir.io/entercard/msp/"

    void setOcirDockerImageName (String name){
        this.ocirDockerImageName = name
    }

    String tenancyNamespace (String env ) {
        switch (env) {
            case ['dev' ,'test']:
                return "${FULL_IMAGE_REPO_URL}tstenv/"
            case 'stage':
                return "${FULL_IMAGE_REPO_URL}stgenv/"
            case 'prod' :
                return "${FULL_IMAGE_REPO_URL}proenv/"
            default:
                return "${FULL_IMAGE_REPO_URL}tstenv/"
        }
    }

}
