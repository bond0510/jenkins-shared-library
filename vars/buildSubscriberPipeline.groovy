import com.test.Git

def call(Map args=[:]) {
  node {
       stage("Checkout") {
            new Git(this).checkout("${args.repo}")
        }
        def p = pipelineCfg()
        stage('Process Properties') {
            println "TEST "
        }
  }
}