import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.Property

void call( Map args=[:] ) {
    ProjectConfiguration projectConfig = args?.projectConfig
    List<Property> propertyList = projectConfig?.properties?.props
    if ( propertyList != null ) {
        propertyList.each { prop ->
            println prop.name
            prop.keys.each { key ->
                script {
                    env.fileName = args.workingDirectory"/"k8s"/"prop.name
                    if ( args?.containsKey (key) ) {
                        env.propertyKey = key
                        value = args?.get (key)
                        env.propertyValue = value
                        println "${key} value is ${value}"
                    } else {
                        println "No property defined for ${key}"
                    }
                }
                sh 'sed -i "s/$propertyKey/$propertyValue/g" $fileName'
                sh ' echo  $fileName  $propertyKey $propertyValue '
            }
        }
    } else {
        println 'No properties are configured for this pipeline'
    }
}
