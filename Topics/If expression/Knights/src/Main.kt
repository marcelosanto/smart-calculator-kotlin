import kotlin.math.abs

fun main() {
    val (y1, x1) = readLine()!!.split(" ").map { it.toInt() }
    val (y2, x2) = readLine()!!.split(" ").map { it.toInt() }

    val y = abs(y2 - y1)
    val x = abs(x2 - x1)

    if (x - y == 1 || y - x == 1) println("YES") else println("NO")
}