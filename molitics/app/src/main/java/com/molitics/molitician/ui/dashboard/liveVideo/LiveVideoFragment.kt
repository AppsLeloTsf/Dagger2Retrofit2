package com.molitics.molitician.ui.dashboard.liveVideo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.molitics.molitician.BR
import com.molitics.molitician.BaseFragment
import com.molitics.molitician.R
import com.molitics.molitician.databinding.FragmentLiveVideoBinding
import com.molitics.molitician.ui.dashboard.liveVideo.videModel.LiveVideoViewModel
import com.molitics.molitician.ui.dashboard.nationalNews.YoutubePlayActivity
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.OnNewsItemClick
import com.molitics.molitician.ui.dashboard.nationalNews.newsRelatedLeadre.newsVideo.MoliticsVideoAdapter
import com.molitics.molitician.util.Constant
import kotlinx.android.synthetic.main.fragment_live_video.*
import javax.inject.Inject


class LiveVideoFragment : BaseFragment<FragmentLiveVideoBinding, LiveVideoViewModel>(), OnNewsItemClick {

    @Inject
    lateinit var liveVideoViewModel: LiveVideoViewModel

    private var loading = true
    var pastVisiblesItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0

    val moliticsLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    lateinit var moliticsVideoAdapter: MoliticsVideoAdapter

    override fun getBindingVariable(): Int = BR.videoViewModel

    override fun getLayoutId(): Int = R.layout.fragment_live_video

    override fun getViewModel(): LiveVideoViewModel = liveVideoViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        liveDataListener()
        liveVideoViewModel.getData()
        recyclerChangeListener()
    }

    private fun setRecyclerView() {
        moliticsVideoAdapter = MoliticsVideoAdapter(this)

        moliticsVideoView.apply {
            layoutManager = moliticsLayoutManager
            adapter = moliticsVideoAdapter
        }
    }

    private fun liveDataListener() {
           liveVideoViewModel.moliticsVideoRepo.observe(this, Observer {
            liveVideoViewModel.setIsLoading(false)
            loading = true
            moliticsVideoAdapter.addData(it)
        })
    }

    private fun recyclerChangeListener() {
        moliticsVideoView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0)
                {
                    visibleItemCount = moliticsLayoutManager.childCount
                    totalItemCount = moliticsLayoutManager.itemCount
                    pastVisiblesItems = moliticsLayoutManager.findFirstVisibleItemPosition()

                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false
                            liveVideoViewModel.getData()
                        }
                    }
                }
            }
        })
    }

    override fun onNewsRecyclerClick(position: Int, type: Int) {
    }

    override fun onVideoClick(position: Int, video_url: String?) {
        val intent = Intent(activity, YoutubePlayActivity::class.java)
        intent.putExtra(Constant.IntentKey.LINK, video_url)
        startActivity(intent)
    }
}