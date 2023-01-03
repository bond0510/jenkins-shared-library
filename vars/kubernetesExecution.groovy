import com.ec.jenkins.components.ProjectConfiguration
import com.ec.jenkins.components.DockerConfig

void call( Map args=[:] ) {
    ProjectConfiguration projectConfig = args?.projectConfig
    DockerConfig dockerCfg = projectConfig.dockerConfig
    String ocirDockerImageName = dockerCfg.ocirDockerImageName
    script {
        file = "${args.workingDirectory}/k8s/app.yaml"
        if ( fileExists (file)) {
           env.fileName = file
           env.imageName = ocirDockerImageName
           sh 'sed -i "s/OCIR_DOCKER_IMAGE_NAME/'$imageName'/g" $fileName'
        }
    }
}
