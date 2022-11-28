import com.ec.Git

def call ( String url=null, String env='dev') {

    new Git().checkout(url, env)
    changelogString = new Git().getChangelogTemplateString()
    echo changelogString
}