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
}

void buildDockerImage ( String imageName, List<String> tags ) {
    tags.each { tag ->
        sh "docker image rm ${imageName}:${tag} "
        sh "docker build --file=docker/Dockerfile.remote -t ${imageName}:${tag} ."
    }
}

void pushDockerImage ( String imageName, String tagVersion ) {
    List<String> tags = [tagVersion.toLowerCase() ,LATEST_STR]
    tags.each { tag ->
        sh "docker build --file=docker/Dockerfile.remote -t ${imageName}:${tag} ."
    }
}

void tagDockerImage ( String imageName, String ocirDockerImageName, List<String> tags ) {
    tags.each { tag ->
        sh "docker tag ${imageName}:${tag} ${ocirDockerImageName}:${tag}"
    }
}
