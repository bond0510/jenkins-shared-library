import com.ec.jenkins.components.services.Configuration
import com.ec.jenkins.components.services.Docker

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
    def dockerCfg = new Configuration(pipelineCfg , env ).getDockerConfig()
    if (workDir == null){
        buildDockerImage( dockerCfg.imageName , TAG_VERSION )
    } else {
        dir(workDir) {
            buildDockerImage( dockerCfg.imageName.toLowerCase() , TAG_VERSION )
        }
    }
    def tenancyNamespace = dockerCfg.tenancyNamespace
    echo tenancyNamespace
}

def buildDockerImage( String imageName, String TAG_VERSION ){

    List<String> tags = [TAG_VERSION.toLowerCase() ,'latest']
    tags.each { tag ->
        sh "docker build --file=docker/Dockerfile.remote -t ${imageName}:${tag} ."
    }

}

def tagDockerImage( String imageName, String TAG_VERSION ){

    List<String> tags = [TAG_VERSION ,'latest']
    tags.each { tag ->
        sh "docker build --file=docker/Dockerfile.remote -t ${imageName}:${tag} ."
    }

}
