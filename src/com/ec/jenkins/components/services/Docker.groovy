package com.ec.jenkins.components.services

import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.parser.ConfigParser

class Docker {

    def steps

    Docker(steps) {
        this.steps = steps
        echo "${env.WORKSPACE}"
    }

}