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
        } else if (inputCalculator.split(" ").size == 1 && !inputCalculator.contains("[0-9]\\+||\\d-".toRegex())) {
            println(inputCalculator)
            continue
        } else if (inputCalculator.split(" ").size == 1 && inputCalculator.contains("-[\\d]|\\+[\\d]".toRegex())) {
            println(inputCalculator.replace("\\+".toRegex(), ""))
            continue
        } else if (inputCalculator.split(" ").size == 1 && inputCalculator.contains("[\\d]-|[\\d]\\+".toRegex()) || inputCalculator.contains(
                "^[a-zA-Z]+".toRegex()
            )
        ) {
            println("Invalid expression")
            continue
        } else if (inputCalculator.contains("/[a-zA-Z]+".toRegex())) {
            println("Unknown command")
        } else {
            calculator(inputCalculator)
        }
    }

    //println("-123".contains("[\\d]-|[\\d]\\+".toRegex()))

}

fun calculator(inputCalculator: String) {
    val numbers = formatCalculator(inputCalculator)
    if (numbers.size == 1) {
        println(inputCalculator)
    } else {
        println(numbers.sum())
    }
}

private fun formatCalculator(
    inputCalculator: String,
): MutableList<Int> {
    val sings = listOf("+", "-")
    val formatNumbers = mutableListOf<String>()
    val numbers = mutableListOf<Int>()

    for (i in inputCalculator.split(" ").toMutableList()) {
        if (i.isNotBlank()) {
            if (i.length > 1) {
                if (sings.contains(i[0].toString()) && sings.contains(i[1].toString())) {
                    if (i[0].toString() == "-") {
                        if (i.length % 2 == 0) formatNumbers.add("+")
                        else formatNumbers.add("-")
                    } else formatNumbers.add(i[0].toString())
                } else formatNumbers.add(i)
            } else formatNumbers.add(i)
        }
    }

    var temp = ""
    for (i in formatNumbers) {
        temp = if (temp.isBlank() && sings.contains(i)) {
            i
        } else if (!sings.contains(i[0].toString())) {
            numbers.add("$temp$i".toInt())
            ""
        } else {
            if (i[0].toString() == "+" && temp == "+" || i[0].toString() == "-" && temp == "-") {
                numbers.add("+${i.substring(1)}".toInt())
                ""
            } else {
                numbers.add("-${i.substring(1)}".toInt())
                ""
            }
        }
    }

    return numbers
}
