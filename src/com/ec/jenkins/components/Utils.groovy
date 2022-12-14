package com.ec.jenkins.components

static Map parseConfig(def body = [:]) {
    Map config = [:]
    config = body
    if (body in Map) {
        config = body
        } else if (body in Closure) {
        body.resolveStrategy = Closure.DELEGATE_FIRST
        body.delegate = config
        body()
        } else {
        throw  new Exception(sprintf('Unsupported build config type:%s', [config.getClass()]))
    }
    return config
}
