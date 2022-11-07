package com.tsfapps.myapplication

import java.math.BigInteger

fun main(){
    val n = 6
    val a = BigInteger("0")
    val b = BigInteger("1")

    print("Fibonacci of $n is = ")

    println(fibonacci(n, a, b))

    print("Factorial of $n = ${factorial(n = n)}")

}

tailrec fun fibonacci(n: Int, a: BigInteger, b: BigInteger): BigInteger{
    return if (n == 0) a else fibonacci(n-1, b, a+b)
}

tailrec fun factorial(n: Int, run: Int = 1): Long{
    return if (n == 1) run.toLong() else factorial(n-1, run*n)
}
