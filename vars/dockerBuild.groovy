import com.ec.jenkins.components.services.Configuration
import com.ec.jenkins.components.services.Docker

def call ( String env , String workDir) {

    TAG_VERSION = readMavenPom().getVersion()
    def pipelineCfg
    if (workDir == null){
        pipelineCfg = readYaml(file: "${WORKSPACE}/pipeline.yaml")
    } else {
        dir(workDir) {
            pipelineCfg = readYaml(file: "${WORKSPACE}/${workDir}/pipeline.yaml")
        }
    }
    def dockerCfg = new Configuration(pipelineCfg.dockerConfig,env).getDockerConfig()
    if (workDir == null){
        buildDockerImage( dockerCfg.imageName() , TAG_VERSION )
    } else {
        dir(workDir) {
            buildDockerImage( dockerCfg.imageName() , TAG_VERSION )
        }
    }
}

def buildDockerImage( String imageName, String TAG_VERSION ){
    def docker = new Docker()
    List<String> tags = [TAG_VERSION ,'latest']
    docker.build(imageName , tags)
}
