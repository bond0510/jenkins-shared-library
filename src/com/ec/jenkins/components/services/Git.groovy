package com.ec.jenkins.components.services

void checkout(Map args=[:]) {
    url = "${args.repo}"
    env = "${args.env}"
    Map scmVars
    if (url == null) {
       scmVars = checkout scm
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
        scmVars = checkout poll: true, scm: [
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
    echo "${scmVars.GIT_COMMIT}"
    args.put('GIT_COMMIT',"${scmVars.GIT_COMMIT}")
}
