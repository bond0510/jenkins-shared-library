def call() {
  node {
      def p = pipelineCfg()
      stage('Checkout') {
        println "TEST "
      }
  }
}