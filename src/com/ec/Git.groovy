package com.ec;

def checkout(String url=null, String env=test) {
        if (url == null) {
            checkout scm
        } else {
            switch (env) {
                case 'dev':
                    branch = 'refs/heads/develop'
                    break
                case 'test':
                    branch = 'refs/heads/test'
                    break
                case 'stage':
                    branch = 'refs/heads/stage'
                    break
                case 'prod' :
                    branch = 'refs/heads/master'
                    break
                default:
                    branch = 'refs/heads/develop'
                    break
            }
            echo "${branch}"
            checkout poll: true, scm: [
                                        $class: 'GitSCM',
                                        branches: [
                                                    [name: branch]
                                                 ],
                                        doGenerateSubmoduleConfigurations: true,
                                        extensions: [],
                                        submoduleCfg: [],
                                        userRemoteConfigs: [
                                                                [credentialsId: 'Bitbucket_SSHkey', url: url]
                                                            ]
                                    ]
        }

        def getChangelogTemplateString( ) {
            return """
                <h1> TEST </h1>
                <p>
                    Changelog of TEST .
                </p>
                {{#tags}}
                <h2> {{name}} </h2>
                {{#issues}}
                    {{#hasIssue}}
                    {{#hasLink}}
                        <h2> {{name}} <a href="{{link}}">{{issue}}</a> {{title}} </h2>
                    {{/hasLink}}
                    {{^hasLink}}
                        <h2> {{name}} {{issue}} {{title}} </h2>
                    {{/hasLink}}
                {{/hasIssue}}
                {{^hasIssue}}
                    <h2> {{name}} </h2>
                {{/hasIssue}}
                {{#commits}}
                    <a href="TEST/commit/{{hash}}">{{hash}}</a> {{authorName}} <i>{{commitTime}}</i>
                    <p>
                        <h3>{{{messageTitle}}}</h3>
                        {{#messageBodyItems}}
                            <li> {{.}}</li>
                        {{/messageBodyItems}}
                    </p>
                {{/commits}}
                {{/issues}}
            {{/tags}}
            """
        }
}
