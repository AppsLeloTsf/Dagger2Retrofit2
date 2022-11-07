package com.tsfapps.rxjavaretrofitpractice

data class Student(val id: Id, val name: String, val SubscribedCourse: List<Course>)
data class Course(val id: Id, val name: String, val isPaid: Boolean)
typealias Id = Int
