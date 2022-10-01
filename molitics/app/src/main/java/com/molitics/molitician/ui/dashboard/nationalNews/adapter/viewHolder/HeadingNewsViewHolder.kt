package com.molitics.molitician.ui.dashboard.nationalNews.adapter.viewHolder

import android.content.Intent
import android.os.Bundle
import com.molitics.molitician.base.BaseViewHolder
import com.molitics.molitician.databinding.HeadingnewsPagerBinding
import com.molitics.molitician.model.News
import com.molitics.molitician.ui.dashboard.nationalNews.YoutubePlayActivity
import com.molitics.molitician.ui.dashboard.nationalNews.adapter.HeaderFragmentPagerAdapter
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.OnNewsItemClick
import com.molitics.molitician.ui.dashboard.newsDetail.DetailNewsActivity
import com.molitics.molitician.util.Constant
import kotlinx.android.synthetic.main.headingnews_pager.view.*

class HeadingNewsViewHolder(val mBinding: HeadingnewsPagerBinding) : BaseViewHolder(mBinding.root) {

    override fun onBind(position: Int, dataList: Any?) {
        val data = dataList as ArrayList<News>
        with(itemView) {
            val headingAdapter = HeaderFragmentPagerAdapter(itemView.context, data, object : OnNewsItemClick {
                override fun onVideoClick(position: Int, video_url: String?) {
                    val intent = Intent(itemView.context, YoutubePlayActivity::class.java)
                    intent.putExtra(Constant.IntentKey.LINK, video_url)
                    itemView.context.startActivity(intent)
                }

                override fun onNewsRecyclerClick(position: Int, type: Int) {
                    val intent = Intent(itemView.context, DetailNewsActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable(Constant.IntentKey.NEWS_LIST, data)
                    intent.putExtra(Constant.IntentKey.NEWS_POSITION, position)
                    intent.putExtras(bundle)
                    itemView.context.startActivity(intent)
                }
            })
            hor_recycler_view.adapter = headingAdapter
        }

    }

}