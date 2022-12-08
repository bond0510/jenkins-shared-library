import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.DockerConfig
import java.time.*

void call ( Map args=[:] ) {
    ProjectConfiguration projectConfig = args?.projectConfig
    DockerConfig dockerCfg = projectConfig.dockerConfig
    String dockerImageName =  dockerCfg.imageName.toLowerCase()
    String tagVersion = args.TAG_VERSION
    String evnVal = args.env
    List<String> tags = [tagVersion.toLowerCase() ,'latest']
    String commitID = args.GIT_COMMIT
    dir(args.workingDirectory) {
        buildDockerImage( dockerImageName,tags,commitID.trim(),tagVersion.trim() )
    }
    stage('Tag Docker Image') {
        String tenancyNamespace = dockerCfg.tenancyNamespace(evnVal)
        String ocirDockerImageName = tenancyNamespace + dockerImageName
        tagDockerImage(dockerImageName , ocirDockerImageName , tags)
    }
    stage('Push Docker Image') {
        String tenancyNamespace = dockerCfg.tenancyNamespace(evnVal)
        String ocirDockerImageName = tenancyNamespace + dockerImageName
        pushDockerImage(ocirDockerImageName,tags)
    }
}

void buildDockerImage ( String imageName, List<String> tags,String commitID,String buildVersion ) { 
    deleteDockerImage(imageName,tags)
    String currentTime = getTimeStamp().trim()
    tags.each { tag ->
        sh "docker build --build-arg BUILD_DATE=${currentTime} --build-arg VCS_REF=${commitID} --build-arg BUILD_VERSION=${buildVersion} --file=docker/Dockerfile.remote  -t ${imageName}:${tag} ."
    }
}

void deleteDockerImage (String imageName, List<String> tags){
    tags.each { tag ->
        sh """
            docker image inspect ${imageName}:${tag} >/dev/null 2>&1 && docker images  ${imageName}:${tag} --filter=reference=image_name --format "{{.ID}}" | xargs --no-run-if-empty docker rmi -f || echo NO
        """
    }
}

void pushDockerImage ( String ocirDockerImageName, List<String> tags ) {
    tags.each { tag ->
        withDockerRegistry(credentialsId: 'oicr_creds', url: 'https://fra.ocir.io'){
            sh "docker push ${ocirDockerImageName}:${tag}"
        }
    }
}

void tagDockerImage ( String imageName, String ocirDockerImageName, List<String> tags ) {
    tags.each { tag ->
        sh "docker tag ${imageName}:${tag} ${ocirDockerImageName}:${tag}"
    }
}

String getTimeStamp() {
   Date date = new Date()
   return date.format('yyyy-MM-dd:HH:mm:ss',TimeZone.getTimeZone('UTC')) as String
}
