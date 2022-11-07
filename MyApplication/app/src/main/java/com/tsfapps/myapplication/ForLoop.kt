package com.tsfapps.myapplication

fun main(){
   /* for (i in 1..5){
        println(i)
    }*/
    /*for (i in 20 downTo 0 step 2){
        println(i)
    }
    */
 /*   var lang = arrayOf("JAVA", "KOTLIN", "C", "C++", "PYTHON", "RUBY")
    var numbers = intArrayOf(25, 10, 20, 5, 15, 35)
    var sum: Int = 0
   *//* for (item in lang)
        println(item)*/
/*

    for (item in lang.indices){
        if (item%2 == 0)
            println(lang[item])
    }
    for (i in numbers.indices){
        sum += numbers[i]
        print("${numbers[i]} + ")
    }

    println(" = $sum")*/
  /*  val Name: String = "Tousif Akram"
    for (i in Name.indices){
        println("${Name[i]}")
         if (i == 5){
             break
         }
    }*/
   /* var sum = 0
    var num: Int
   first@ while (true){
        print("Enter a number: ")
        num = readLine()!!.toInt()
        if (num == 0)
            break@first
        sum += num
    }

    print("Sum = $sum")
*/
  /*  first@ for (i in 1..4){
        tsf@ for (j in 1..2){
            println("i = $i; j = $j")

            if (i == 2){
                break@tsf
            }
        }
    }*/
   /* for ( i in 1..5){
        println("Well Done!")
        if (i in 2..4){
            continue
        }
        println("Not Well!")
    }*/
  /*  var number: Int
    var sum = 0
    for (i in 1..6){
        print("Enter an integer: ")
        number = readLine()!!.toInt()
        if (number <= 0)
            continue
        sum += number
    }
    println("Sum = $sum")*/
   /*here@ for (i in 1..5){
        for (j in 1..4){
            if (i == 3 || j == 2)
                continue@here
            println("i = $i; j = $j")
        }

    }*/

    val list: MutableList<Int> = ArrayList()
    val listNew: MutableList<Int> = ArrayList()

    print("Enter size of array: ")
    var sizeArr: Int = readLine()!!.toInt()
    var i: Int = 0
    while (i < list.size) {
        var element: Int = readLine()!!.toInt()
        list.add(i, element)
        i++
// Adds Hello as the third item in list
    }
    for (i in sizeArr-1 downTo 0){
        listNew.add(list[i])
    }
    println(list)
    println(listNew)

    for (i in 0 until listNew.size){
        println(listNew[i])
    }
}