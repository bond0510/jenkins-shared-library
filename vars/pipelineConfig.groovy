import com.ec.jenkins.components.parser.ConfigParser
import com.ec.jenkins.components.ProjectConfiguration

Map call( Map args=[:] ) {
  Map pipelineCfg
  String workdir = args.workDir
  if (workdir == null) {
    pipelineCfg = readYaml(file: "${WORKSPACE}/pipeline.yaml")
    args.put('workingDirectory', "${ WORKSPACE}" )
  } else {
    dir(workdir) {
      pipelineCfg = readYaml(file: "${WORKSPACE}/${workdir}/pipeline.yaml")
      args.put('workingDirectory', "${WORKSPACE }/${workdir }" )
    }
  }
  ProjectConfiguration projectConfig = ConfigParser.parse(pipelineCfg)
  args.put('projectConfig' , projectConfig)
  return args
}
