import com.test.Git

def call(Map args=[:]) {
  node {
       stage("Checkout") {
            println "REPO : ${args.repo}"
            new Git(this).checkout("${args.repo}")
        }
        def p = pipelineCfg()
        stage('Process Properties') {
            println "TEST "
        }
  }
}