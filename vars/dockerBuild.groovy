import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.DockerConfig

void call ( Map args=[:] ) {
    ProjectConfiguration projectConfig = args?.projectConfig
    DockerConfig dockerCfg = projectConfig.dockerConfig
    String dockerImageName =  dockerCfg.imageName.toLowerCase()
    String tagVersion = args.TAG_VERSION
    String evnVal = args.env
    List<String> tags = [tagVersion.toLowerCase() ,'latest']
    dir(args.workingDirectory) {
        buildDockerImage( dockerImageName , tags )
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

void buildDockerImage ( String imageName, List<String> tags ) { 
    deleteDockerImage(imageName,tags)
    currentTime = getTimeStamp()
    tags.each { tag ->
        sh """
            docker build --file=docker/Dockerfile.remote --build-arg BUILD_DATE= ${currentTime} -t ${imageName}:${tag} ."
        """
    }
}

void deleteDockerImage (String imageName, List<String> tags){
    tags.each { tag ->
        sh """
            docker image inspect ${imageName}:${tag} >/dev/null 2>&1 && docker images  ${imageName}:${tag} --filter=reference=image_name --format "{{.ID}}" | xargs --no-run-if-empty docker rmi -f || echo NO
        """
    }
}

void pushDockerImage ( String ocirDockerImageName, tags ) {
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
   return date.format('yyyyMMddHHmmss',TimeZone.getTimeZone('UTC')) as String
}