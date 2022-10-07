def call(Map args=[:]) {

  node {
       stage("Checkout") {
            println "REPO : ${args.repo}"
            git branch: 'master',
                url: "${args.repo}"
        }
        def p = pipelineCfg()
        stage('Process Properties') {
            println "TEST "
        }
  }

}