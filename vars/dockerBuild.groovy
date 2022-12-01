import com.ec.jenkins.components.parser.ConfigParser
import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.DockerConfig


def call ( Map args=[:] ) {

    ProjectConfiguration projectConfig = args?.projectConfig

    DockerConfig dockerCfg = projectConfig.dockerConfig

    String dockerImageName =  dockerCfg.imageName.toLowerCase()

    def workdir = args.workDir

    def tagVersion = args.TAG_VERSION

    def evnVal= args.env

    if (workdir == null){
        buildDockerImage( dockerImageName , tagVersion )
    } else {
        dir(workdir) {
            buildDockerImage( dockerImageName , tagVersion )
        }
    }

    def tenancyNamespace = dockerCfg.tenancyNamespace(evnVal)
    def ocirDockerImageName = tenancyNamespace + dockerImageName

    tagDockerImage(dockerImageName , ocirDockerImageName , tagVersion)

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
