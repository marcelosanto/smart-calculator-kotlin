fun main() {
    val text = readLine()!!
    val regexColors = "#[\\da-fA-F]{6}\\b".toRegex()
    // write your code here
    val result = regexColors.findAll(text)
    for (res in result) {
        println(res.value)
    }

}