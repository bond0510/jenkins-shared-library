package com.ec.jenkins.components.services

import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.parser.ConfigParser

class Docker {

    def steps

    Docker(steps) {
        this.steps = steps
        
        echo "workspace directory is ${steps.env.WORKSPACE}"
        echo "build URL is ${steps.env..BUILD_URL}"
    }

}