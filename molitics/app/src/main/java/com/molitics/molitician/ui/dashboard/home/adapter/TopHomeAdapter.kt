package com.molitics.molitician.ui.dashboard.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molitics.molitician.base.BaseViewHolder
import com.molitics.molitician.database.NewsLeaderModel
import com.molitics.molitician.databinding.AdapterTrendingLeaderBinding
import com.molitics.molitician.ui.dashboard.home.viewHolder.TrendingLeaderViewHolder
import java.util.ArrayList

class TopHomeAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private var trendingLeaderModel = ArrayList<NewsLeaderModel>()

    fun addTrendingLeaderData(staffModel: List<NewsLeaderModel>) {
        this.trendingLeaderModel = staffModel as ArrayList<NewsLeaderModel>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = AdapterTrendingLeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrendingLeaderViewHolder(view)
    }

    override fun getItemCount(): Int = trendingLeaderModel.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(holder.adapterPosition, trendingLeaderModel)
    }
}