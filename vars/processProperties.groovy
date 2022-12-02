import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.Property

void call( Map args=[:] ) {
    ProjectConfiguration projectConfig = args?.projectConfig
    List<Property> propertyList = projectConfig?.properties?.props
    println args.param.MESSAGING_SERVER
    if ( propertyList != null ) {
        propertyList.each { prop ->
            println prop.name
            prop.keys.each { key ->
                script {
                    env.fileName = prop.name
                    println args.param.key
                    if ( args?.param?.containsKey (key) ) {
                        env.propertyKey = key
                    } else {
                        println "No property defined for ${key}"
                    }
                }
                sh ' echo  $fileName $propertyKey '
            }
        }
    } else {
        println 'No properties are configured for this pipeline'
    }
}
