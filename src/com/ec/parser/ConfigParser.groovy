package com.ec.parser

import com.ec.*

class ConfigParser {

     static ProjectConfiguration parse(def yaml) {
        ProjectConfiguration projectConfiguration = new ProjectConfiguration()
        projectConfiguration.dockerConfig = parseDockerConfig(projectConfiguration)
        projectConfiguration.properties = parseProperties(yaml.Properties)

        return projectConfiguration
     }


    static def parseProperties(def properties) {
        List<Property> props = properties.collect { k, v ->
            Property property = new Property(name: k)
            // a step can have one or more commands to execute
            v.each {
                /* groovylint-disable-next-line ImplicitClosureParameter */
                property.commands.add(it)
            }
            return property
        }
        return new Properties(props: props)
    }

    
    static def parseDockerConfig(def projectConfiguration){
        return new DockerConfig(projectConfiguration: projectConfiguration)
    }

}
