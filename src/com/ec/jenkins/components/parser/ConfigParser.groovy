/* groovylint-disable DuplicateStringLiteral */
package com.ec.jenkins.components.parser

import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.Property
import com.ec.jenkins.components.Properties
import com.ec.jenkins.components.DockerConfig

class ConfigParser {

     @NonCPS
     static ProjectConfiguration parse(def yaml) {
        ProjectConfiguration projectConfiguration = new ProjectConfiguration()

        projectConfiguration.dockerConfig = parseDockerConfig( yaml.DockerConfig )

        projectConfiguration.properties = parseProperties(yaml.Properties)

        projectConfiguration.secretes = parseProperties(yaml.Secretes)

        return projectConfiguration
     }

    @NonCPS
    static def parseProperties(def properties) {
        List<Property> props = properties.collect { k, v ->
            Property property = new Property(name: k)
            v.each {
                property.keys.add(it)
            }
            return property
        }
        return new Properties(props: props)
    }

    @NonCPS
    static def parseDockerConfig(def dockerConfig){

        if (!dockerConfig || !dockerConfig['dockerImageName']) {
            return "Dockerfile"
        }
        return new DockerConfig(imageName: dockerConfig['dockerImageName'])
    }

}
