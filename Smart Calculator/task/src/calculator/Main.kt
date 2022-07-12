package calculator

fun main() {
    val letras = Regex("[a-zA-Z]+")
    val listDeVariaveis = mutableMapOf<String, Int>()

    while (true) {
        val str = readln().trimEnd()
        if (str.isBlank()) {
            continue
        } else if ("[a-zA-Z]+\\d+".toRegex().find(str)?.value != null) {
            println("Invalid identifier")
        } else if ("[a-zA-Z]+\\s*=\\s*([a-zA-Z]|\\d)+\\s*=\\s*([a-zA-Z]|\\d)+".toRegex()
                .find(str)?.value != null || "[a-zA-Z]+\\s*=\\s*([a-zA-Z]+\\d+|\\d+[a-zA-Z]+)".toRegex()
                .find(str)?.value != null || "=".toRegex().findAll(str).count() > 1
        ) {
            println("Invalid assignment")
        } else if (letras.find(str)?.value == null) {
            println(calculatorFunction(str))
        } else if (letras.find(str)?.value != null && "/[a-zA-Z]".toRegex().find(str)?.value == null) {
            if ("=".toRegex().find(str)?.value != null) {
                val (x, y) = str.replace("\\s*".toRegex(), "").split("=")
                if (listDeVariaveis[y] == null && "[a-zA-Z]".toRegex().find(y)?.value != null) {
                    println("Unknown variable")
                } else if (listDeVariaveis[y] != null) {
                    listDeVariaveis[x] = listDeVariaveis[y]!!.toInt()
                } else listDeVariaveis[x] = y.toInt()
            } else {
                val regex = Regex("[a-zA-z]+")
                val matches = regex.findAll(str)
                val lettersVariables = matches.map { it.groupValues[0] }.toMutableList()

                var text = str

                for (i in lettersVariables.iterator()) {
                    text = if (listDeVariaveis[i] != null) {
                        text.replace(i, listDeVariaveis[i].toString())
                    } else {
                        "Unknown variable"
                    }
                }
                if (regex.find(text)?.value == null) {
                    println(calculatorFunction(text))
                } else println(text)
            }
        } else if ("/[a-zA-Z]".toRegex().find(str)?.value != null) {
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
        }
    }

}

fun formatMultipleSigns(calculatorNumbers: String): String {
    val multipleSigns = Regex("[+|-]{2,}")

    var tempNumbersMultipleSings = calculatorNumbers

    if (multipleSigns.find(tempNumbersMultipleSings)?.value != null) {
        val temp = multipleSigns.find(tempNumbersMultipleSings)?.value

        tempNumbersMultipleSings = if (temp!!.matches("-+".toRegex())) {
            if (temp!!.length % 2 == 0) {
                tempNumbersMultipleSings.replace(temp, "+")
            } else tempNumbersMultipleSings.replace(temp, "-")
        } else tempNumbersMultipleSings.replace(temp, "+")
    }

    return tempNumbersMultipleSings
}

fun calculatorFunction(str: String): String {
    val noParentheses = Regex("(^-?\\d+\\s*(\\+|-|\\*|/|\\^)\\s*\\d+)+")
    val withParentheses = Regex("(\\(\\s*\\d+\\s*(\\+|-|\\*|/|\\^)\\s*\\d+\\s*(\\+|-|\\*|/|\\^)?\\s*\\d*\\s*\\))")
    val multipleSigns = Regex("[+|-]{2,}")
    var result = str

    while (true) {
        if ("[*/^]{2,}".toRegex().find(result)?.value != null || "[(]".toRegex()
                .find(result)?.value != null && "[)]".toRegex().find(result)?.value == null || "[(]".toRegex()
                .find(result)?.value == null && "[)]".toRegex().find(result)?.value != null
        ) {
            result = "Invalid expression"
        } else if (multipleSigns.find(result)?.value != null) {
            result = formatMultipleSigns(result)
        } else if (withParentheses.find(result)?.value != null) {
            result = sumWithParentheses(result, withParentheses)
        } else if (noParentheses.find(result)?.value != null) {
            result = calcNumbers(result)
        } else {
            break
        }
    }

    return result
}

fun calcNumbers(numbers: String): String {
    var calcTemp: Int
    var result: String

    val regexMulti = Regex("-?\\d+\\s*\\*\\s*\\d+")
    val regexDiv = Regex("-?\\d+\\s*/\\s*\\d+")
    val regexElev = Regex("-?\\d+\\s*\\^\\s*\\d+")


    val calculator: (Int, Int, String) -> Int = function()

    if (regexElev.find(numbers)?.value != null) {
        val (x, y) = regexElev.find(numbers)?.value?.replace("\\s*".toRegex(), "")?.split("^")!!.map { it.toInt() }
        val temp = regexElev.find(numbers)?.value
        result = numbers.replace(temp!!, calcPow(x, y).toString())

    } else if (regexMulti.find(numbers)?.value != null) {
        val teste = regexMulti.find(numbers)?.value?.replace("\\s*".toRegex(), "")?.replace("*", " * ")?.split(" ")
            ?.toMutableList()

        val temp = regexMulti.find(numbers)?.value

        calcTemp = teste?.let { calculator(teste[0].toInt(), teste[2].toInt(), teste[1]) }!!
        result = numbers.replace(temp!!, calcTemp.toString())


    } else if (regexDiv.find(numbers)?.value != null) {
        val teste = regexDiv.find(numbers)?.value?.replace("\\s*".toRegex(), "")?.replace("/", " / ")?.split(" ")
            ?.toMutableList()
        val temp = regexDiv.find(numbers)?.value

        calcTemp = teste?.let { calculator(teste[0].toInt(), teste[2].toInt(), teste[1]) }!!
        result = numbers.replace(temp!!, calcTemp.toString())
    } else {
        val listInt = mutableListOf<Int>()

        numbers.replace("\\s+".toRegex(), "").replace("(-\\+|\\+-)".toRegex(), "-").replace("-", ",-")
            .replace("+", ",+").replace("^,".toRegex(), "")
            .split(",").map { listInt.add(it.toInt()) }

        result = listInt.sum().toString()
    }
    return result
}

fun calcPow(x: Int, y: Int): Int {
    //valor x elevado
    var h = 1

    for (i in 1..y) {
        h *= x
    }

    return h
}

private fun function(): (Int, Int, String) -> Int {
    val calculator: (Int, Int, String) -> Int = { number01: Int, number02: Int, operation: String ->

        when (operation) {
            "*" -> number01 * number02
            "/" -> number01 / number02
            else -> 0
        }
    }
    return calculator
}

fun sumWithParentheses(str: String, regex: Regex): String {
    val parents = regex.find(str)?.value

    var parentes = parents?.replace("(", "")?.replace(")", "").let { calcNumbers(it!!) }

    if (parentes.split(" ").toMutableList().size > 1) {
        parentes = "($parentes)"
    }

    return str.replace(parents!!, parentes)
}
