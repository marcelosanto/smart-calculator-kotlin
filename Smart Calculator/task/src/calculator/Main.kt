package calculator

fun main() {
    while (true) {
        val inputCalculator = readln()

        if (inputCalculator == "/help") {
            println("The program calculates the sum of numbers")
            continue
        } else if (inputCalculator == "/exit") {
            println("Bye!")
            break
        } else if (inputCalculator.isBlank()) {
            continue
        } else {
            calculator(inputCalculator)
        }
    }


}

fun calculator(inputCalculator: String) {
    val numbers = mutableListOf<Int>()

    if (inputCalculator.length == 1) {
        println(inputCalculator)
    } else {
        inputCalculator.split(" ").map { numbers.add(it.toInt()) }
        println(numbers.sum())
    }
}
