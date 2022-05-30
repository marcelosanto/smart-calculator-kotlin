fun main() {
    val x = readln().toInt()
    val y = readln().toInt()
    val z = readln().toInt()
    
    val sum = x + y
    val sum2 = y + z
    val sum3 = x + z
    
    if (sum > z && sum2 > x && sum3 > y) println("YES") else println("NO")
}
