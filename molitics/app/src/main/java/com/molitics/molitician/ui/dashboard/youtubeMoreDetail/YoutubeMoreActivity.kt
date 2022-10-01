package com.molitics.molitician.ui.dashboard.youtubeMoreDetail

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.molitics.molitician.BR
import com.molitics.molitician.BaseActivity
import com.molitics.molitician.R
import com.molitics.molitician.databinding.ActivityYoutubeMoreBinding
import com.molitics.molitician.model.NewsVideoModel
import com.molitics.molitician.ui.dashboard.youtubeMoreDetail.adapter.YoutubeMoreAdapter
import com.molitics.molitician.ui.dashboard.youtubeMoreDetail.viewModel.SendDataBody
import com.molitics.molitician.ui.dashboard.youtubeMoreDetail.viewModel.YoutubeMoreViewModel
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.Util
import kotlinx.android.synthetic.main.activity_youtube_more.*
import javax.inject.Inject

class YoutubeMoreActivity : BaseActivity<ActivityYoutubeMoreBinding, YoutubeMoreViewModel>() {

    @Inject
    lateinit var youtubeMoreViewModel: YoutubeMoreViewModel

    @Inject
    lateinit var youtubeMoreAdapter: YoutubeMoreAdapter

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    private var from: String = Util.YoutubeType.national.name
    private var candidateId: Int? = null
    private var trendingId: Int? = null

    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var pastVisiblesItems: Int = 0
    private var opendVideoId: Int = 0

    private var loading = true

    override fun getBindingVariable(): Int {
        return BR.youtubViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_youtube_more
    }

    override fun getViewModel(): YoutubeMoreViewModel {
        return youtubeMoreViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindAdapter()
        subscribeYoutubeLive()
        ///val mNews: List<News> = intent.getSerializableExtra(Constant.IntentKey.NEWS_LIST) as List<News>
        val mNews: List<NewsVideoModel> = listOf(intent.getParcelableExtra<NewsVideoModel>(Constant.IntentKey.NEWS_VIDEO))
        from = intent.getStringExtra(Constant.IntentKey.FROM)
        candidateId = intent.getIntExtra(Constant.IntentKey.CANDIDATE_ID, 0)
        trendingId = intent.getIntExtra(Constant.IntentKey.TRENDING_ID, 0)
        youtubeMoreViewModel.youtubeLiveList.value = mNews
        opendVideoId = mNews[0].id
        getDataFromServer()
        recyclerChangeListener()
    }

    private fun bindAdapter() {
        mLayoutManager.orientation = RecyclerView.VERTICAL
        youtubeVideoRecycler.apply {
            layoutManager = mLayoutManager
            adapter = youtubeMoreAdapter
        }
    }

    private fun getDataFromServer() {
        youtubeMoreViewModel.getVideoListFromServer(SendDataBody(opendVideoId, from, candidateId
                ?: 0, trendingId ?: 0))
    }

    private fun subscribeYoutubeLive() {
        youtubeMoreViewModel.youtubeLiveList.observe(this, Observer {
            loading = true
            youtubeMoreViewModel.setYoutubeList(it)
        })
    }

    private fun recyclerChangeListener() {
        youtubeVideoRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0)
                //check for scroll down
                {
                    visibleItemCount = mLayoutManager.childCount
                    totalItemCount = mLayoutManager.itemCount
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition()

                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false
                            getDataFromServer()
                        }
                    }
                }
            }
        })
    }
}