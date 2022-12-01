import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.DockerConfig

private string LATEST_STR = 'latest'

void call ( Map args=[:] ) {
    ProjectConfiguration projectConfig = args?.projectConfig
    DockerConfig dockerCfg = projectConfig.dockerConfig
    String dockerImageName =  dockerCfg.imageName.toLowerCase()
    String workdir = args.workDir
    String tagVersion = args.TAG_VERSION
    String evnVal = args.env
    if (workdir == null) {
        buildDockerImage( dockerImageName , tagVersion )
    } else {
        dir(workdir) {
            buildDockerImage( dockerImageName , tagVersion )
        }
    }

    String tenancyNamespace = dockerCfg.tenancyNamespace(evnVal)
    String ocirDockerImageName = tenancyNamespace + dockerImageName

    tagDockerImage(dockerImageName , ocirDockerImageName , tagVersion)
}

void buildDockerImage ( String imageName, String tagVersion ) {
    List<String> tags = [tagVersion.toLowerCase() ,LATEST_STR]
    tags.each { tag ->
        sh "docker build --file=docker/Dockerfile.remote -t ${imageName}:${tag} ."
    }
}

void pushDockerImage ( String imageName, String tagVersion ) {
    List<String> tags = [tagVersion.toLowerCase() ,LATEST_STR]
    tags.each { tag ->
        sh "docker build --file=docker/Dockerfile.remote -t ${imageName}:${tag} ."
    }
}

void tagDockerImage ( String imageName, String ocirDockerImageName, String tagVersion ) {
    List<String> tags = [tagVersion.toLowerCase() ,LATEST_STR]
    tags.each { tag ->
        sh "docker tag ${imageName}:${tag} ${ocirDockerImageName}:${tag}"
    }
}
