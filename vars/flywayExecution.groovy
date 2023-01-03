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
                    CREDENTIALS_ID = getCredentialsId(key)
                    DATABASE_IP_KEY =getCredentialsId('DATABASE_KUBE_SRV') 
                    withCredentials([usernamePassword(credentialsId: key, passwordVariable: 'PASSWORD_VAL', usernameVariable: 'USERNAME_VAL'),
                                      string(credentialsId: DATABASE_IP_KEY, variable: 'DATABASE_IP_VALUE')]) {
                        withEnv(['JAVA_HOME=/usr/java/jdk']) {
                            sh ' mvn  -Ddatasource.url="jdbc:mysql://${DATABASE_IP_VALUE}/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&characterEncoding=UTF-8" -Dspring.datasource.username=${USERNAME_VAL} -Dspring.datasource.password=${PASSWORD_VAL} clean package -P flyway -DskipTests=true '
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
