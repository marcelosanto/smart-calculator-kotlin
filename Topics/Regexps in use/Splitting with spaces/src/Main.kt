fun main() {
    val string = readln()
    val n = readln().toInt()
    val stringReplaceWitSplit = string.split("\\s+".toRegex(), n)

    for (i in stringReplaceWitSplit) {
        println(i)
    }
}