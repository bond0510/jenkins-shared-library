import com.ec.Configuration


def call ( def  pipelineCfg ) {

    def dockerCfg = new Configuration(pipelineCfg.dockerConfig).dockerConfig()
    
    echo dockerCfg
}
