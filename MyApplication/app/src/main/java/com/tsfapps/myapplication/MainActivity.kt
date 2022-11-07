package com.tsfapps.myapplication

fun main(args: Array<String>) {
/*    var i = 1
    while (i <= 10){
        println("Line $i")
        ++i
    }

    var sum = 0
   // var j = 10

    while (i != 0){
        sum += i
        --i
    }

    println("Sum = $sum")*/


    var sum: Int = 0
    var input: String

   do {
       print("Enter the number")
       input = readLine()!!
       sum += input.toInt()
   } while( input != "0")

   println("Sum = $sum")


}