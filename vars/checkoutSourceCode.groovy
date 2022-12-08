import com.ec.jenkins.components.services.Git

def call ( Map args=[:]) {
    println args.repo 
    println args.env
    new Git().checkout(args)

}