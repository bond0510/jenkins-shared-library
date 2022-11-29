package com.ec.jenkins.components.services

import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.parser.ConfigParser

class Docker {

    def steps

    Docker( steps , Closure body=null ) {
        this.steps = steps
        echo "build URL is ${env.BUILD_NUMBER}"
        echo "build URL is ${steps.env.BUILD_NUMBER}"
        
    }

}