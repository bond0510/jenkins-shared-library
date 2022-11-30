package com.ec.jenkins.components

class DockerConfig {

    String imageName

    public static FULL_IMAGE_REPO_URL = "fra.ocir.io/entercard/msp/"

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
