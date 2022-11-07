package com.tsfapps.myapplication

fun main(args: Array<String>){

    val list: MutableList<Int> = ArrayList()
    val N = readLine()!!.toInt()

    var i = 0
    while (i < N){
        val num = readLine()!!.toInt()
        list.add(num)
        i++

    }
    println(list)


    for (j in 0 until list.size){
        for (k in j+1 until list.size){
            var tempVal: Int
            if (list[k] < list[j]){
                tempVal = list[k]
                list[k] = list[j]
                list[j] = tempVal
            }
        }
    }
    print(list)
}