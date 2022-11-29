package com.ec.jenkins.components.services

import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.parser.ConfigParser

class Docker {

    def build ( String imageName=null , String tag='latest' ) {
        echo "DOCKER IMAGE NAME : ${imageName}:${tag}"
        //sh "docker build --file=docker/Dockerfile.remote -t ${imageName}:${tag} ."
    }

}
