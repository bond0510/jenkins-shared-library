import com.ec.jenkins.components.parser.ConfigParser
import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.DockerConfig


def call ( String env , String workDir) {

    def pipelineCfg
    if (workDir == null){
        TAG_VERSION = readMavenPom().getVersion()
        pipelineCfg = readYaml(file: "${WORKSPACE}/pipeline.yaml")
    } else {
        dir(workDir) {
            TAG_VERSION = readMavenPom().getVersion()
            pipelineCfg = readYaml(file: "${WORKSPACE}/${workDir}/pipeline.yaml")
        }
    }
    ProjectConfiguration projectConfig = ConfigParser.parse(pipelineCfg)

    DockerConfig dockerCfg = projectConfig.dockerConfig

    String dockerImageName =  dockerCfg.imageName.toLowerCase();
    if (workDir == null){
        buildDockerImage( dockerImageName , TAG_VERSION )
    } else {
        dir(workDir) {
            buildDockerImage( dockerImageName , TAG_VERSION )
        }
    }
    def tenancyNamespace = dockerCfg.tenancyNamespace(env)
    def ocirDockerImageName = tenancyNamespace + dockerImageName
    tagDockerImage(dockerImageName , ocirDockerImageName , TAG_VERSION)
}

def buildDockerImage( String imageName, String TAG_VERSION ){

    List<String> tags = [TAG_VERSION.toLowerCase() ,'latest']
    tags.each { tag ->
        sh "docker build --file=docker/Dockerfile.remote -t ${imageName}:${tag} ."
    }

}

def tagDockerImage( String imageName, ocirDockerImageName,String TAG_VERSION ){

    List<String> tags = [TAG_VERSION ,'latest']
    tags.each { tag ->
        sh "docker tag ${imageName}:${tag} ${ocirDockerImageName}:${tag}"
    }

}
