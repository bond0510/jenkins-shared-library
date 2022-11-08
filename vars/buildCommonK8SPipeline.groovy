import java.lang.String

def call(Map args=[:]) {

  node {
		stage('Code Checkout') { // for display purposes
			println "REPO : ${args.repo}"
			checkout poll: true, scm: [$class: 'GitSCM', branches: [[name: 'refs/heads/test']], doGenerateSubmoduleConfigurations: true, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'Bitbucket_SSHkey', url: "${args.repo}"]]]
		}
        def p = pipelineConfig()
        stage('Process Properties') {
            def properties = "${p?.Properties}"
			echo "$properties"
        }
          
        
    }

 
}
