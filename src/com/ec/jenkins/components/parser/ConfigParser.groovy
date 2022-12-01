/* groovylint-disable DuplicateStringLiteral */
package com.ec.jenkins.components.parser

import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.Property
import com.ec.jenkins.components.Properties
import com.ec.jenkins.components.DockerConfig

class ConfigParser {

     @NonCPS
    static ProjectConfiguration parse( Map yaml ) {
        ProjectConfiguration projectConfiguration = new ProjectConfiguration()

        projectConfiguration.dockerConfig = parseDockerConfig( yaml.DockerConfig )

        projectConfiguration.properties = parseProperties(yaml.Properties)

        projectConfiguration.secretes = parseProperties(yaml.Secretes)

        return projectConfiguration
    }

    @NonCPS
    static Properties parseProperties(Map properties) {
        List<Property> props = properties.collect { k, v ->
            Property property = new Property(name: k)
            v.each {
                property.keys.add (it)
            }
            return property
        }
        return new Properties(props: props)
    }
    
    @NonCPS
    static DockerConfig parseDockerConfig(Map dockerConfig){
        if (!dockerConfig || !dockerConfig['dockerImageName']) {
            return "Dockerfile"
        }
        return new DockerConfig(imageName: dockerConfig['dockerImageName'])
    }

}
