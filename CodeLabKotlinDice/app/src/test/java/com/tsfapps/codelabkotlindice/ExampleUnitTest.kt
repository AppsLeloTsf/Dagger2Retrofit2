package com.tsfapps.codelabkotlindice

import org.junit.Test

import org.junit.Assert.*

class ExampleUnitTest {
   /* @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }*/

    @Test
    fun generates_number(){

        val dice = Dice(6)
        val rollNum = dice.roll()
        assertTrue("The value of rollNum is not between 1 to 6", rollNum in 1..6)
    }
}