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
            def secerts = "${p?.secerts}"

            Map secertsMap = convertToMap(secerts)
            for (entry in secertsMap) {
                println "KeyName: $entry.key = Value: $entry.value"
            }
        }
    }

 
}