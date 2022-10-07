import java.lang.String

def call(Map args=[:]) {

  node {
       stage("Checkout") {
            println "REPO : ${args.repo}"
            git branch: 'master',
                url: "${args.repo}"
        }
        def p = pipelineConfig()
        stage('Process Properties') {
            println "TEST ${p?.applicationName}"
            println "TEST ${p?.secerts?.properties2}"
            println "TEST ${p?.properties?.properties2}"
            def secertsMap = "${p?.secerts}"
            println secertsMap
            secertsMap.each { key, val ->
                 println "Key: $key , Value: $val"
            }

            for (def e in mapToList(secertsMap)){    
                println "key = ${e.key}, value = ${e.value}"
            }
        }
	}

 
}