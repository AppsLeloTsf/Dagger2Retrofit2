package com.tsfapps.mvvm_hilt_retrofit_coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsfapps.mvvm_hilt_retrofit_coroutine.adapter.PostAdapter
import com.tsfapps.mvvm_hilt_retrofit_coroutine.databinding.ActivityMainBinding
import com.tsfapps.mvvm_hilt_retrofit_coroutine.ui.ApiState
import com.tsfapps.mvvm_hilt_retrofit_coroutine.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var postAdapter: PostAdapter
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        mainViewModel.getPost()
       lifecycleScope.launchWhenStarted {
           mainViewModel._postStateFlow.collect{ it->
               when(it){
                   is ApiState.Loading->{
                       binding.rvPosts.isVisible=false
                       binding.progressBar.isVisible = true
                   }
                   is ApiState.Failure->{
                       binding.rvPosts.isVisible=false
                       binding.progressBar.isVisible = false
                       Toast.makeText(this@MainActivity, "Error: ${it.msg}", Toast.LENGTH_SHORT).show()
                       Log.d("TSF_APPS_MAIN", "Error: ${it.msg}")
                   }
                  is ApiState.Empty -> {

                  }
                   is ApiState.Success -> {
                       binding.rvPosts.isVisible=true
                       binding.progressBar.isVisible = false
                       postAdapter.setData(it.data)
                   }
               }

           }
       }
    }

    private fun initRecyclerView() {
       postAdapter = PostAdapter(ArrayList())
        binding.rvPosts.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = postAdapter
        }
    }
}