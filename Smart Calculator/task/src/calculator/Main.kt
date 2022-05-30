package calculator

fun main() {
    while (true) {
        val inputNumbers = readln()
        val numbers = mutableListOf<Int>()

        if (inputNumbers == "/exit") {
            println("Bye!")
            break
        } else if (inputNumbers.isNullOrBlank()) continue
        else {
            inputNumbers.split(" ").map { numbers.add(it.toInt()) }
        }

        if (numbers.size == 1) println(numbers[0])
        else {
            val (x, y) = numbers
            println(x + y)
        }
    }
}
