package com.molitics.molitician.ui.dashboard.localNews

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import com.molitics.molitician.BuildConfig
import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.R
import com.molitics.molitician.base.BaseViewHolder
import com.molitics.molitician.databinding.AdapterNewsVerticalBinding
import com.molitics.molitician.model.News
import com.molitics.molitician.ui.dashboard.nationalNews.YoutubePlayActivity
import com.molitics.molitician.ui.dashboard.newsDetail.DetailNewsActivity
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.ExtraUtils
import com.molitics.molitician.util.ShareScreenShot
import com.molitics.molitician.util.Util.attachLeaderToLinearView
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_news_vertical.view.*

class LocalNewsViewHolder(mBinding: AdapterNewsVerticalBinding) : BaseViewHolder(mBinding.root) {

    lateinit var dataList: MutableList<News>
    val thumbnailViewToLoaderMap = HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>()

    init {
        mBinding.thumbnail.initialize(MolticsApplication.YOUTUBE_KEY, ThumbnailListener())
    }

    override fun onBind(news_position: Int, dataObject: Any?) {
        dataList = dataObject as MutableList<News>

        val newsItem = dataList[news_position]
        with(itemView) {
            main_constraintLayout.visibility = View.VISIBLE
            main_constraintLayout.tag = news_position
            news_headline.text = newsItem.heading
            sponser_news_post_time.text = newsItem.time
            channelNameView.text = newsItem.source
            channelNameView.tag = newsItem

            attachLeaderToLinearView(itemView.context, attachedLeaderView, newsItem.taggedLeader)

            shareNewsView.setOnClickListener {
                newsItem.shareLink?.let {
                    shareNews(itemView.context, it)
                }
            }

            if (!TextUtils.isEmpty(newsItem.sourceLogo)) {
                Picasso.with(channelImageView.context).load(newsItem.sourceLogo).into(channelImageView)
            }


            when {
                newsItem.type == 1 -> {
                    video_play_icon.visibility = View.GONE
                    thumbnail.visibility = View.GONE
                    loadImage(itemView.context, newsItem.image, news_image)

                    thumbnail.visibility = View.GONE
                }
                newsItem.type == 2 -> {
                    video_play_icon.visibility = View.VISIBLE
                    thumbnail.visibility = View.VISIBLE
                    val loader = thumbnailViewToLoaderMap[thumbnail]
                    if (loader == null) {
                        thumbnail.tag = newsItem.link
                    } else {
                        thumbnail.setImageResource(R.drawable.loading_thumbnail)
                        try {
                            loader.setVideo(newsItem.link)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                }
                newsItem.type != 0 -> {
                    video_play_icon.visibility = View.GONE
                    thumbnail.visibility = View.GONE
                    loadImage(itemView.context, newsItem.image, news_image)

                    sponser_news_post_time.text = ExtraUtils.setBoldPinkLastString(itemView.context, newsItem.time, itemView.context.getString(R.string.publish_by_user), 1f)
                }
            }
            main_constraintLayout.setOnClickListener { v ->
                val currentPosition = main_constraintLayout.tag as Int
                detailNewsActivity(itemView.context, currentPosition)
            }
            news_vi_view.setOnClickListener { startYoutubeVideo(news_vi_view.context, newsItem.videoLink) }

        }
    }

    private fun shareNews(mContext: Context, url: String) {
        ShareScreenShot.takeScreenshot(mContext, mContext.getString(R.string.media_of_politics), url)

    }

    private fun loadImage(mContext: Context, url: String?, news_image: ImageView) {
        //show image
        if (url != null && !TextUtils.isEmpty(url)) {
            Picasso.with(mContext).load(url).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.image_placeholder).error(R.drawable.internet_no_cloud).into(news_image, object : Callback {
                override fun onSuccess() {}

                override fun onError() {
                    Picasso.with(mContext).load(url).placeholder(R.drawable.image_placeholder).error(R.drawable.internet_no_cloud).into(news_image)
                }
            })
        }
    }

    private fun detailNewsActivity(mContext: Context, newsPosition: Int) {
        val bundle = Bundle()
        bundle.putSerializable(Constant.IntentKey.NEWS_LIST, dataList as ArrayList<News>)
        val intent = Intent(mContext, DetailNewsActivity::class.java)
        intent.putExtra(Constant.IntentKey.NEWS_POSITION, newsPosition)
        intent.putExtras(bundle)
        mContext.startActivity(intent)
    }

    private fun startYoutubeVideo(context: Context, video_url: String) {
        val intent = Intent(context, YoutubePlayActivity::class.java)
        intent.putExtra(Constant.IntentKey.LINK, video_url)
        context.startActivity(intent)
    }

    private inner class ThumbnailListener : YouTubeThumbnailView.OnInitializedListener, YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        override fun onInitializationSuccess(
                view: YouTubeThumbnailView, loader: YouTubeThumbnailLoader) {
            loader.setOnThumbnailLoadedListener(this)
            thumbnailViewToLoaderMap[view] = loader
            view.setImageResource(R.drawable.video_placeholder)
            val videoId = view.tag as String?
            videoId?.let {
                loader.setVideo(it)
            }
        }

        override fun onInitializationFailure(
                view: YouTubeThumbnailView?, loader: YouTubeInitializationResult) {
            view?.setImageResource(R.drawable.video_placeholder)
        }

        override fun onThumbnailLoaded(view: YouTubeThumbnailView, videoId: String) {

        }

        override fun onThumbnailError(view: YouTubeThumbnailView?, errorReason: YouTubeThumbnailLoader.ErrorReason) {
            view?.setImageResource(R.drawable.video_placeholder)
        }

    }

}