import java.lang.String

def call(Map args=[:]) {

  node {
       stage("Checkout") {
            println "REPO : ${args.repo}"
            git branch: 'master',
                url: "${args.repo}"
        }
        def p = pipelineConfig()
        stage('Process Secerts') {
            def secerts = "${p?.secerts}"
            Map secertsMap = convertToMap(secerts) 
            for (entry in secertsMap) {
                println "KeyName: $entry.key = Value: $entry.value"
            }
        }

        stage('Process Properties') {
            def properties = "${p?.properties}"
            Map propertiesMap = convertToMap(properties) 
                           
                dir('k8s/tstenv'){
                    for (entry in propertiesMap) {
                         println "KeyName: $entry.key = Value: $entry.value"
                        sh 'sed -i "s/$entry.key/$entry.value}/g" test-basic-configmap.yaml'
                    } 
                }
        
        }   
            
        
    }

 
}