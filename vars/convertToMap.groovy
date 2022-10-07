def call(depmap) {
    Map mapResult=[:]
    mapResult += secertsMap.replaceAll('\\[|\\]', '').split(',').collectEntries { entry ->
        def pair = entry.split(':')
        [(pair.first().trim()): pair.last().trim()]
    }
    mapResult
}