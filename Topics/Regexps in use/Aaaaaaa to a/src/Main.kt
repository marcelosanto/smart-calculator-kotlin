fun main() {
    val text = readln()
    val processedString = text.replace("a?Aa|AA?".toRegex(), "a").replace("a+|A".toRegex(), "a")
    println(processedString)
}
