package com.ec.jenkins.components.services

import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.parser.ConfigParser

class Docker {

    def steps

    Docker( steps , Closure body=null ) {
        this.steps = steps
        steps.env.BUILD_NUMBER = steps.sh(script: '${BUILD_NUMBER}', returnStdout: true).trim()
        echo "build URL is ${steps.env.BUILD_NUMBER}"
        
    }

}