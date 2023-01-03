import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.Property

void call( Map args=[:] ) {
    ProjectConfiguration projectConfig = args?.projectConfig
    List<Property> propertyList = projectConfig?.secretes?.props
    if ( propertyList != null ) {
        propertyList.each { prop ->
            println prop.name
            prop.keys.each { key ->
                script {
                    file = "${args.workingDirectory}/k8s/${prop.name}"
                    if ( fileExists (file)) {
                        env.fileName = file
                        env.credentialsId = getCredentialsId(key)
                        env.propertyKey = key
                        withCredentials([string(credentialsId: '${e.credentialsId}', variable: 'property_value')]) {
                            sh 'sed -i "s/$propertyKey/$property_value/g" $fileName'
                        }
                    }
                }
                sh ' echo  $fileName $propertyKey '
            }
        }
    } else {
        println 'No properties are configured for this pipeline'
    }
}

String getCredentialsId(String key) {
    switch (env) {
        case ['dev' ,'test']:
           return "${key}_TEST"
        case 'stage':
           return "${key}_STAGE"
        case 'prod' :
           return "${key}_PROD"
        default:
            return "${key}_TEST"
    }
}
