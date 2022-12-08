import com.ec.jenkins.components.services.Git

def call ( Map args=[:]) {

    new Git().checkout(args)

}