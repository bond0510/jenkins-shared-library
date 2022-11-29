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

    buildDockerImage( dockerCfg.imageName() , TAG_VERSION )

    switch (env) {
        case 'dev':
            break
        case 'test':
            branch = 'refs/heads/test'
            break
        case 'stage':
            branch = 'refs/heads/stage'
            break
        case 'prod' :
            branch = 'refs/heads/master'
            break
        default:
            branch = 'refs/heads/develop'
            break
    }
}

def buildDockerImage( String imageName, String TAG_VERSION ){
    def docker = new Docker()
    List<String> tags = [TAG_VERSION ,'latest']
    docker.build(imageName , tags)
}
