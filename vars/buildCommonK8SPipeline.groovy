import java.lang.String
import java.util.Map

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
			def mapAsString = properties.toMapString()
			Map map =
			// Take the String value between
			// the [ and ] brackets.
			mapAsString[1..-2]
				// Split on , to get a List.
				.split(', ')
				// Each list item is transformed
				// to a Map entry with key/value.
				.collectEntries { entry ->
					def pair = entry.split(':')
					[(pair.first()): pair.last()]
				}
			echo "${map}"
			
        }
          
        
    }

 
}
