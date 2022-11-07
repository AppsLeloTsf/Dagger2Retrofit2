package com.tsfapps.mvvm_hilt_retrofit_coroutine.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tsfapps.mvvm_hilt_retrofit_coroutine.databinding.RawRvPostsBinding
import com.tsfapps.mvvm_hilt_retrofit_coroutine.models.Post

class PostAdapter(private var postList: List<Post>):  RecyclerView.Adapter<PostAdapter.PostViewHolder>(){

    private lateinit var binding: RawRvPostsBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setData(postList: List<Post>){
        this.postList = postList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        binding = RawRvPostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        binding.tvPostsBody.text = postList[position].body
    }

    override fun getItemCount(): Int = postList.size


    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}