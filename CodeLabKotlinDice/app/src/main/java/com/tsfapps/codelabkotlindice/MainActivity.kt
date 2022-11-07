package com.tsfapps.codelabkotlindice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rollButton: Button = findViewById(R.id.button)
        rollButton.setOnClickListener { rollDice() }
        rollDice()
    }

    fun rollDice(){
        val dice = Dice(6)
        val rollNum = dice.roll()
//        val luckyNumber = 3
        val diceNumber: TextView = findViewById(R.id.tvDiceNum)
        diceNumber.setText(rollNum.toString())
        val diceImage: ImageView = findViewById(R.id.ivDiceImage)
      /*  if (rollNum>3) {
            val toast = Toast.makeText(this, "Great performance", Toast.LENGTH_SHORT)
            toast.show()
        }else{
            val toast = Toast.makeText(this, "Ohh! Try again", Toast.LENGTH_SHORT)
            toast.show()
        }*/
        val drawableRes = when (rollNum) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        diceImage.setImageResource(drawableRes)
        diceImage.contentDescription = rollNum.toString()
    }
}