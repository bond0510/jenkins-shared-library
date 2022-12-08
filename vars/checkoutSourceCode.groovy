import com.ec.jenkins.components.services.Git

def call ( Map args=[:]) {
    Map scmVars= new Git().checkout(args.repo, args.env)
    args.put('GIT_COMMIT',"${scmVars.GIT_COMMIT}")
}