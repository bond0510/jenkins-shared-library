import com.ec.jenkins.components.services.Configuration

def call ( def pipelineCfg , String TAG_VERSION,String env) {

    def dockerCfg = new Configuration(pipelineCfg.dockerConfig,env).getDockerConfig()

    switch (env) {
        case 'dev':
            buildDockerImage( dockerCfg.imageName() ,TAG_VERSION )
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
    echo " Building ${imageName} docker image of version ${TAG_VERSION} "
    sh " docker build --file=docker/Dockerfile.remote -t ${imageName}:${TAG_VERSION} ."
}
