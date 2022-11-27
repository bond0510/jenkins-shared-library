import com.ec.Git

def call ( String url=null, String env="dev") {
    new Git().checkout(url, env)
}