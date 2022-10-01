package com.molitics.molitician.ui.dashboard.youtubeMoreDetail.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.molitics.molitician.base.BaseViewHolder
import com.molitics.molitician.databinding.AdapterYoutubeMoreBinding
import com.molitics.molitician.model.News
import com.molitics.molitician.model.NewsVideoModel
import com.molitics.molitician.ui.dashboard.newsDetail.DetailNewsActivity
import com.molitics.molitician.ui.dashboard.youtubeMoreDetail.viewHolder.AdapterYoutubeViewHolder
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.ExtraUtils
import java.util.ArrayList

class YoutubeMoreAdapter(val lifecycle: Lifecycle) : RecyclerView.Adapter<BaseViewHolder>() {

    private var youtubeVideoList: MutableList<NewsVideoModel> = mutableListOf()
    private var currentPosition = 0

    fun addData(youtubeList: List<NewsVideoModel>) {
        youtubeVideoList.clear()
        youtubeVideoList.addAll(youtubeList)
        notifyDataSetChanged()
    }

    fun getData(position: Int) = youtubeVideoList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val mView = AdapterYoutubeMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        lifecycle.addObserver(mView.youtubePlayerView)
        mView.headingTxt.setOnClickListener {
            val bundle = Bundle()
            val sendNewsList = ExtraUtils.convertNewsVideoToNewsModule(youtubeVideoList)

            val intent = Intent(parent.context, DetailNewsActivity::class.java)
            bundle.putSerializable(Constant.IntentKey.NEWS_LIST, sendNewsList)
            intent.putExtra(Constant.IntentKey.NEWS_POSITION, currentPosition)
            intent.putExtras(bundle)
            parent.context.startActivity(intent)
        }

        return AdapterYoutubeViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return youtubeVideoList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        currentPosition = holder.adapterPosition
        holder.onBind(position, youtubeVideoList)
    }
}