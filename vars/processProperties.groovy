import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.Property
import com.ec.jenkins.components.Properties

def call( Map args=[:] ) {
    ProjectConfiguration projectConfig = args?.projectConfig
    List<Property> properties = projectConfig.properties.props
    properties.each { prop ->
        println prop.name
        prop.keys.each { key ->
            script {
                env.fileName = prop.name
                if ( args.containsKey (key) ) {
                    env.propertyKey = key
                } else {
                    println "No property defined for ${key}"
                }
            }
            sh ' echo  $fileName $propertyKey '
        }
    }
}