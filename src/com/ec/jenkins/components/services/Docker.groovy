package com.ec.jenkins.components.services

import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.parser.ConfigParser

class Docker {

    @NonCPS
    def build ( String image, List<String> tags=['latest'] ) {

        tags.each { tag ->
            sh "docker build --file=docker/Dockerfile.remote -t ${image}:${tag} ."
        }
    }

}
