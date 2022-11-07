package com.tsfapps.clickcallbackpractice

fun main(){

    println(isLetter('A'))
    println(isLetter('*'))

}

fun isLetter(char: Char) = char in 'a'..'z' || char in 'A'..'Z'