import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.Property

void call( Map args=[:] ) {
    ProjectConfiguration projectConfig = args?.projectConfig
    List<Property> propertyList = projectConfig?.credentials?.props
    if ( propertyList != null ) {
        propertyList.each { prop ->
            println prop.name
            prop.keys.each { key ->
                script {
                    file = "${args.workingDirectory}/k8s/${prop.name}"
                    if ( fileExists (file)) {
                        env.fileName = file
                        CREDENTIALS_ID = getCredentialsId(key)
                        env.USERNAME = "${key}_USERNAME"
                        env.PASSWORD = "${key}_PASSWORD"
                        withCredentials([usernamePassword(credentialsId: key, passwordVariable: 'PASSWORD_VAL', usernameVariable: 'USERNAME_VAL')]) {
					        sh 'sed -i "s/$USERNAME/$USERNAME_VAL/g" $fileName'
				            sh 'sed -i "s/$PASSWORD/$PASSWORD_VAL/g" $fileName'
                        }
			        }
                }
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
