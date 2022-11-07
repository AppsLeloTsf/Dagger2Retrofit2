package com.tsfapps.myapplication


fun main(){
    print("Enter the number: ")
    val number = readLine()!!.toInt()
    val result: Long

    result = factorial(number)
    print("Factorial = $result")
}

 fun factorial(number: Int): Long{
     return if (number == 1) number.toLong() else number* factorial(number-1)
 }