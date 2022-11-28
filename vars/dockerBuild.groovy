import com.ec.Configuration


def call ( def  pipelineCfg) {
    new Configuration(pipelineCfg).dockerConfig()
}
