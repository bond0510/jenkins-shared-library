import com.ec.jenkins.components.parser.ConfigParser
import com.ec.jenkins.components.ProjectConfiguration

def call( Map args=[:] ) {

  def pipelineCfg

  if (workDir == null){
      pipelineCfg = readYaml(file: "${WORKSPACE}/pipeline.yaml")
  } else {
    dir(workDir) {
      pipelineCfg = readYaml(file: "${WORKSPACE}/${workDir}/pipeline.yaml")
    }
  }
  ProjectConfiguration projectConfig = ConfigParser.parse(pipelineCfg)
  args.put('projectConfig' , projectConfig)
  return args
}
