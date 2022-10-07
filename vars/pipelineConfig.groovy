def call() {
  Map pipelineCfg = readYaml(file: "${WORKSPACE}/pipeline.yaml"), returnPojo: true
  return pipelineCfg
}