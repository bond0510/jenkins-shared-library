package com.ec.jenkins.components.services

Map checkout(String url=null, String env=test) {
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
    return scmVars
}
