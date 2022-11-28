import com.ec.Configuration

@NonCPS
def call ( def  pipelineCfg) {
    new Configuration(pipelineCfg).dockerConfig()
}
