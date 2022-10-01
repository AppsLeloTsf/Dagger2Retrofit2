package com.molitics.molitician.ui.dashboard.youtubeMoreDetail.viewHolder

import android.content.Intent
import android.os.Bundle
import com.molitics.molitician.base.BaseViewHolder
import com.molitics.molitician.databinding.AdapterYoutubeMoreBinding
import com.molitics.molitician.model.News
import com.molitics.molitician.model.NewsVideoModel
import com.molitics.molitician.ui.dashboard.newsDetail.DetailNewsActivity
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.ExtraUtils
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.adapter_youtube_more.view.*

class AdapterYoutubeViewHolder(mBinding: AdapterYoutubeMoreBinding) : BaseViewHolder(mBinding.root) {

    private var youTubePlayerL: YouTubePlayer? = null
    private var currentVideoId: String? = null
    private var playVideoId: String? = null

    init {
        mBinding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayerL = youTubePlayer
                currentVideoId?.let {
                    youTubePlayer.cueVideo(it, 0f)
                    if (it == playVideoId)
                        youTubePlayer.play()
                }
            }
        })
    }

    override fun onBind(position: Int, dataObject: Any?) {
        val mNews = dataObject as List<NewsVideoModel>

        /// this.mBinding.youtubeDetailViewModel = mNews
        ///   this.mBinding.executePendingBindings()
        with(itemView) {
            currentVideoId = mNews[position].link
            if (position == 0) {
                playVideoId = currentVideoId
            }
            headingTxt.text = mNews[position].heading

            headingTxt.setOnClickListener {
                val bundle = Bundle()
                val sendNewsList = ExtraUtils.convertNewsVideoToNewsModule(mNews)

                val intent = Intent(headingTxt.context, DetailNewsActivity::class.java)
                bundle.putSerializable(Constant.IntentKey.NEWS_LIST, sendNewsList)
                intent.putExtra(Constant.IntentKey.NEWS_POSITION, position)
                intent.putExtras(bundle)
                headingTxt.context.startActivity(intent)
            }

            if (youTubePlayerL == null)
                return

            youTubePlayerL?.cueVideo(currentVideoId!!, 0f)
        }
    }
}