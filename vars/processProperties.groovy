import com.ec.jenkins.Utils

def call(Closure body=null) {

    def config = new Utils().parseConfig(body) 
    def jdkName = config?.jdkName ?: "jdk"
    println jdkName

}