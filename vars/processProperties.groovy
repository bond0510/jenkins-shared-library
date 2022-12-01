import com.ec.jenkins.components.Utils

def call(Closure body=null) {

    def config = new Utils().parseConfig(body) 
    println config 

}