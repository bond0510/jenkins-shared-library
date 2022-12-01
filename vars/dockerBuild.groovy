import com.ec.jenkins.components.parser.ConfigParser
import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.DockerConfig


def call ( Map args=[:] ) {

    ProjectConfiguration projectConfig = args?.projectConfig

    DockerConfig dockerCfg = projectConfig.dockerConfig

    String dockerImageName =  dockerCfg.imageName.toLowerCase()

    def workdir = args.workDir

    if (workdir == null){
        buildDockerImage( dockerImageName , TAG_VERSION )
    } else {
        dir(workdir) {
            buildDockerImage( dockerImageName , TAG_VERSION )
        }
    }

    def tenancyNamespace = dockerCfg.tenancyNamespace(env)
    def ocirDockerImageName = tenancyNamespace + dockerImageName

    tagDockerImage(dockerImageName , ocirDockerImageName , TAG_VERSION)

}

def buildDockerImage ( String imageName, String TAG_VERSION ) {

    List<String> tags = [TAG_VERSION.toLowerCase() ,'latest']
    tags.each { tag ->
        sh "docker build --file=docker/Dockerfile.remote -t ${imageName}:${tag} ."
    }

}

def pushDockerImage ( String imageName, String TAG_VERSION ) {

    List<String> tags = [TAG_VERSION.toLowerCase() ,'latest']
    tags.each { tag ->
        sh "docker build --file=docker/Dockerfile.remote -t ${imageName}:${tag} ."
    }

}

def tagDockerImage ( String imageName, ocirDockerImageName,String TAG_VERSION ) {

    List<String> tags = [TAG_VERSION.toLowerCase() ,'latest']
    tags.each { tag ->
        sh "docker tag ${imageName}:${tag} ${ocirDockerImageName}:${tag}"
    }

}
