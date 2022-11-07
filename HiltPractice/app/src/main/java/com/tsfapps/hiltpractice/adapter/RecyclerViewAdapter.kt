package com.tsfapps.hiltpractice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tsfapps.hiltpractice.R
import com.tsfapps.hiltpractice.di.network.RecyclerData
import kotlinx.android.synthetic.main.raw_recycler.view.*

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){

    private var listData: List<RecyclerData>? = null

    fun setListData(listData: List<RecyclerData>?){
        this.listData = listData
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.raw_recycler, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(listData?.get(position)!!)
    }

    override fun getItemCount(): Int {
       return listData?.size!!
    }

    inner class ViewHolder(view: View) :RecyclerView.ViewHolder(view){
        var tvName = view.tvName
        var tvDescription = view.tvDescription
        var ivImage = view.ivImage
        fun bind(data: RecyclerData){
            tvName.text = data.name
            tvDescription.text = data.description
            Glide.with(ivImage)
                .load(data.owner?.avatar_url)
                .into(ivImage)

        }
    }
}