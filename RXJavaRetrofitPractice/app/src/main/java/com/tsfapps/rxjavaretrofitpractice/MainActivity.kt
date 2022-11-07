package com.tsfapps.rxjavaretrofitpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.tsfapps.rxjavaretrofitpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

   private fun initRecyclerView(){
       binding.rvBookList.apply {
           val layoutManager: LinearLayoutManager = LinearLayoutManager(this@MainActivity)
           val decoration = DividerItemDecoration(this@MainActivity, VERTICAL)
           addItemDecoration(decoration)



       }

    }
}