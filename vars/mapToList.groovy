def call(depmap) {
    def dlist = []
    for (def entry2 in depmap) {
        println entry2 
        dlist.add(new java.util.AbstractMap.SimpleImmutableEntry(entry2.key.toString(), entry2.value.toString()))
    }
    dlist
}