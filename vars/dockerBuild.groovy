import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.DockerConfig
import java.time.*

void call ( Map args=[:] ) {
    ProjectConfiguration projectConfig = args?.projectConfig
    DockerConfig dockerCfg = projectConfig.dockerConfig
    String dockerImageName =  dockerCfg.imageName.toLowerCase()
    String tagVersion = args.TAG_VERSION
    dockerCfg.tag = tagVersion.toLowerCase()
    String evnVal = args.env
    List<String> tags = [tagVersion.toLowerCase() ,'latest']
    String commitID = args.GIT_COMMIT
    dir(args.workingDirectory) {
        if (evnVal == 'test') {
            buildDockerImage( dockerImageName , tags,commitID.trim(),tagVersion.trim() )
        }
    }
    stage('Tag Docker Image') {
        String sourceDockerImageName = dockerCfg.sourceImageName(evnVal , dockerImageName )
        String targetDockerImageName = dockerCfg.targetImageName(evnVal , dockerImageName )
        tagDockerImage(sourceDockerImageName , targetDockerImageName , tags)
    }
    stage('Push Docker Image') {
        String targetDockerImageName = dockerCfg.targetImageName(evnVal , dockerImageName )
        pushDockerImage(targetDockerImageName , tags)
    }
}

void buildDockerImage ( String imageName, List<String> tags,String commitID,String buildVersion ) { 
    deleteDockerImage(imageName , tags)
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

boolean checkDockerImageExists (String imageName, String tag){
  found = sh([returnStdout: true, script: " docker image inspect ${imageName}:${tag} >/dev/null 2>&1 &&  echo found || echo notfound"]).trim()
  if (found == 'found') {
      return true
  }
  return false
}

void pushDockerImage ( String ocirDockerImageName, List<String> tags ) {
    tags.each { tag ->
        withDockerRegistry(credentialsId: 'oicr_creds', url: 'https://fra.ocir.io'){
            sh "docker push ${ocirDockerImageName}:${tag}"
        }
    }
}

void tagDockerImage ( String sourceImageName, String targetImageName, List<String> tags ) {
    tags.each { tag ->
        if ( checkDockerImageExists (sourceImageName , tag) ){
            println "docker image found"
        } else {
            println "docker image not found"
        }
        sh "docker tag ${sourceImageName}:${tag} ${targetImageName}:${tag}"
    }
}

String getTimeStamp() {
   Date date = new Date()
   return date.format('yyyy-MM-dd:HH:mm:ss',TimeZone.getTimeZone('UTC')) as String
}
