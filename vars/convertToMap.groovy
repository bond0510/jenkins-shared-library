def call(depmap) {
    Map mapResult=[:]
    mapResult += depmap.replaceAll('\\[|\\]', '').split(',').collectEntries { entry ->
        def pair = entry.split(':')
        [(pair.first().trim()): pair.last().trim()]
    }
    mapResult
}