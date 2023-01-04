import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.DockerConfig

void call( Map args=[:] ) {
    ProjectConfiguration projectConfig = args?.projectConfig
    DockerConfig dockerCfg = projectConfig.dockerConfig
    String dockerImageName = dockerCfg.imageName
    String dockerImageTag = dockerCfg.tag
    script {
        file = "${args.workingDirectory}/k8s/app.yaml"
        if ( fileExists (file)) {
           env.fileName = file
           env.IMAGENAME = dockerImageName
           env.IMAGETAG = dockerImageTag
           env.ENVNAME = getEnv(args.env)
           sh 'sed -i "s/ENV/$ENVNAME/g" $fileName'
           sh 'sed -i "s/DOCKER_IMAGE_NAME/$IMAGENAME/g" $fileName'
           sh 'sed -i "s/TAG/$IMAGETAG/g" $fileName'
        }
    }
}

String getEnv(String name){
   switch (name) {
        case ['dev' ,'test']:
           return "tstenv"
        case 'stage':
           return "stgenv"
        case 'prod' :
           return "proenv"
        default:
            return "tstenv"
    }
}
