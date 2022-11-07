package com.tsfapps.codelabkotlindice

fun main(){
    val myFirstDice = DiceSide(6)
    println("Your ${myFirstDice.numSide} sided dice rolled ${myFirstDice.roll()}")

    val myFirstDice2 = DiceSide(20)
    println("Your ${myFirstDice2.numSide} sided dice rolled ${myFirstDice2.roll()}")
}

class DiceSide(val numSide: Int){
    fun roll(): Int {
        val num: IntRange = 1..4
        return (1..numSide).random()
    }
}