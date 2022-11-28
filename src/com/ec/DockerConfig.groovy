package com.ec

class DockerConfig {

    ProjectConfiguration projectConfiguration;
    def imageName() {
        "${projectConfiguration.dockerConfig.dockerImageName}".toLowerCase();
    }
}
