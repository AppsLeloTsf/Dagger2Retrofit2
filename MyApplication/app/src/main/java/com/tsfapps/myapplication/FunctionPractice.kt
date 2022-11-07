package com.tsfapps.myapplication

import kotlin.math.sqrt

/*fun addition(a: Int, b: Int): Int{
    return a + b
}*/
fun displayBorder(character: Char = '=', tsf: Int = 15){
    for (i in 1..tsf){
        print(character)
    }
}


fun main(args: Array<String>){
   /* var number: Double
    do {
        print("Enter the number: ")
        number = readLine()!!.toDouble()
        print("Result = ${sqrt(number)}\n")
    }while (number != 0.0)*/
   /* print(addition(7, 8))*/

    displayBorder(tsf = 5)

    println("\n\n '*' is used as argument")
    displayBorder('8',7)

    println("\n\n '*' is used as argument")
    displayBorder('*', 10)


}