package com.ec.jenkins.components.services

class Docker {

    def build ( String imageName=null , String tag='latest' ) {
        sh "docker build --file=docker/Dockerfile.remote -t ${imageName}:${tag} ."
        sh "docker build --file=docker/Dockerfile.remote -t ${imageName}:latest ."
    }

}
