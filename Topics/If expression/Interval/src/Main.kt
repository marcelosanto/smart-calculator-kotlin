fun main() {
    // write your code here
    val x = readln().toInt() 
    
    if (x > -15 && x <= 12) {
        print("True")    
    } else if (x > 14 && x < 17 || x >= 19) {
        print("True")
    } else {
         print("False")
    }
}
