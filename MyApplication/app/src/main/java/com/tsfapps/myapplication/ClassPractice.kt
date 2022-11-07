package com.tsfapps.myapplication

open class Person(FirstName: String, age: Int){

    private var firstName: String = FirstName
    private var myAge: Int = age


    fun showRes(){
            println("My Full Name: $firstName")
            println("My Age: $myAge")
    }


}

data class ClassPractice(val name: String, val age: Int): Person(name, age) {
    var showRes = showRes()

}

fun main(){
    Person("Tousif Akram", 30)

}