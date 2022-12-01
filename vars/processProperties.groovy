import com.ec.jenkins.components.Utils

def call(Closure body=null) {

    def config = new Utils().parseConfig(body) 
    def jdkName = config?.jdkName ?: "jdk"
    def namespace=config?.params.NAMESPACES
    println jdkName namespace

}