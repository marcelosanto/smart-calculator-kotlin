fun main() {
    val text = readln()
    val processedString = text.replace("[aA]+".toRegex(), "a")
    println(processedString)
}
