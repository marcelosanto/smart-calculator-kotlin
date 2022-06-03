package calculator

fun main() {
    val regex =
        Regex("[a-zA-Z]+\\s*?=\\s*?([a-zA-Z]+|\\d+)|[a-zA-Z]+|[a-zA-Z]+\\s*[+-]\\s*[a-zA-Z]+")
    val listDeVariaveis = mutableMapOf<String, Int>()


    while (true) {
        val str = readln().trimEnd()

        if (str.isBlank()) {
            continue
        } else if (str.matches("(([a-zA-Z]*|\\d*)\\s*[+-]?\\s*?){2,}".toRegex())) {
            val calcTemp =
                str.replace("(\\+{2,}|^-^{2})".toRegex(), " + ").replace("-{3}".toRegex(), " - ").split(" ")
                    .toMutableList()
            val listCalc = checarValor(calcTemp, listDeVariaveis)

            if (listCalc != null) println(formatCalculator(listCalc).sum()) else continue

        } else if (regex.matches(str) && !str.matches("/[a-z]+".toRegex())) {
            if (str.matches("[a-zA-Z]+".toRegex()) && listDeVariaveis.containsKey(str)) {
                println(listDeVariaveis[str])
            } else if (str.matches("[a-zA-Z]+".toRegex()) && !listDeVariaveis.containsKey(str)) {
                println("Unknown variable")
            } else {
                val (x, y) = str.replace("\\s*".toRegex(), "").replace("=", " ").split(" ").toMutableList()
                if (y.matches("[a-zA-Z]+".toRegex()) && listDeVariaveis.containsKey(y)) {
                    listDeVariaveis[x] = listDeVariaveis[y]!!.toInt()
                } else if (y.matches("[a-zA-Z]+".toRegex()) && !listDeVariaveis.containsKey(y)) {
                    println("Unknown variable")
                } else listDeVariaveis[x] = y.toInt()
            }
        } else if (str.matches("/[a-z]+".toRegex())) {
            when (str) {
                "/exit" -> {
                    println("bye")
                    break
                }
                "/help" -> {
                    println("The program calculates the sum of numbers")
                    continue
                }
                else -> {
                    println("Unknown command")
                    continue
                }
            }
        } else {
            println("Invalid assignment")
        }
    }


}

fun checarValor(calcTemp: MutableList<String>, listDeVariaveis: MutableMap<String, Int>): MutableList<String>? {
    val calcValue = mutableListOf<String>()

    for (i in calcTemp) {
        if (i.matches("[a-zA-Z]+".toRegex())) {
            if (listDeVariaveis.containsKey(i)) {
                calcValue.add(listDeVariaveis[i].toString())
            } else {
                println("Unknown variable")
                return null
            }
        } else calcValue.add(i)
    }

    return calcValue
}

private fun formatCalculator(
    inputCalculator: MutableList<String>,
): MutableList<Int> {
    val sings = listOf("+", "-")
    val formatNumbers = mutableListOf<String>()
    val numbers = mutableListOf<Int>()

    for (i in inputCalculator) {
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