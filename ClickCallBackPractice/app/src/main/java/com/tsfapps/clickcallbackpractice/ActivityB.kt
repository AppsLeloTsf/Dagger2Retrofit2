package com.tsfapps.clickcallbackpractice

import android.content.Intent
import android.os.Bundle
import android.widget.Toast

class ActivityB : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
        initializeViews()
        dismissProgressDialog()
    }
    private fun initializeViews() {
        setScreenTitle(R.string.activity_b)
        getBackButton().setOnClickListener {
            Toast.makeText(this, "Clicked from MainActivity", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@ActivityB, MainActivity::class.java)
            startActivity(intent)
            showProgressDialog()
            finish()
        }
    }
}