package com.ec.parser

import com.ec.*

class ConfigParser {

     @NonCPS
     static ProjectConfiguration parse(def yaml) {
        ProjectConfiguration projectConfiguration = new ProjectConfiguration()
        projectConfiguration.dockerConfig = parseDockerConfig(yaml)
        projectConfiguration.properties = parseProperties(yaml.Properties)

        return projectConfiguration
     }

    @NonCPS
    static def parseProperties(def properties) {
        List<Property> props = properties.collect { k, v ->
            Property property = new Property(name: k)
            // a step can have one or more commands to execute
            v.each {
                property.commands.add(it)
            }
            return property
        }
        return new Properties(props: props)
    }

    @NonCPS
    static def parseDockerConfig(def projectConfiguration){
        return new DockerConfig(projectConfiguration)
    }

}
