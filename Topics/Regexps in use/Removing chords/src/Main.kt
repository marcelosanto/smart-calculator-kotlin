fun main() {
    val text = readln()
    val regex = Regex("Am\\s|A\\s|Em\\s|E\\s|Dm\\s|D\\s|G\\s|C\\s")
    val textEdit = text.replace(regex, "").replace("\\s{2,}+".toRegex(), " ")
    println(textEdit)
}