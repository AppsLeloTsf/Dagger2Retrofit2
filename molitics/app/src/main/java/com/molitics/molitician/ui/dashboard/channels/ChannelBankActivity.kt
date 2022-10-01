package com.molitics.molitician.ui.dashboard.channels

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.molitics.molitician.BR
import com.molitics.molitician.BaseActivity
import com.molitics.molitician.R
import com.molitics.molitician.databinding.ActivityChannelBankBinding
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass
import com.molitics.molitician.ui.dashboard.channels.viewModel.ChannelBankViewModel
import com.molitics.molitician.ui.dashboard.localNews.LocalNewsAdapter
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.OnNewsItemClick
import com.molitics.molitician.ui.dashboard.newsDetail.DetailNewsActivity
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.ShareScreenShot
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_channel_bank.*
import javax.inject.Inject

class ChannelBankActivity : BaseActivity<ActivityChannelBankBinding, ChannelBankViewModel>(), OnNewsItemClick,
        LocalNewsAdapter.OnLoadMoreResult, BranchShareClass.DeepLinkCallBack {

    @Inject
    lateinit var channelBankViewModel: ChannelBankViewModel

    private var loading = true

    private var newsAdapter: LocalNewsAdapter? = null
    val channelLayoutManager = LinearLayoutManager(this@ChannelBankActivity, RecyclerView.VERTICAL, false)


    override fun getBindingVariable(): Int = BR.channelBankViewModel

    override fun getLayoutId(): Int = R.layout.activity_channel_bank

    override fun getViewModel(): ChannelBankViewModel = channelBankViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val channelId = intent.getIntExtra(Constant.IntentKey.CHANNEL_ID, 0)
        channelBankViewModel.channelId = channelId
        setNewsAdapter()
        setChangeListener()
        setChannelInfo()
        channelBankViewModel.getChannelsNewsRequest()
        setToolBar()
        recyclerChangeListener()

    }

    private fun setToolBar() {
        setSupportActionBar(channelToolBar)
        showHeader(channelToolBar, true, intent.getStringExtra(Constant.IntentKey.SOURCE))
    }


    private fun setChannelInfo() {
        channelNameView.text = intent.getStringExtra(Constant.IntentKey.SOURCE)
        val sourceImage = intent.getStringExtra(Constant.IntentKey.SOURCE_IMAGE)

        sourceImage?.let {
            Picasso.with(this).load(it).into(sourceImageView)
        }
    }

    private fun setNewsAdapter() {
        newsAdapter = LocalNewsAdapter(this, this, this)
        channelNewsRecycler.apply {
            layoutManager = channelLayoutManager
            adapter = newsAdapter
        }
    }

    private fun setChangeListener() {
        channelBankViewModel.channelNews.observe(this, Observer {
            loading = true
            newsAdapter?.addLocalNews(it)
        })
    }


    override fun onNewsRecyclerClick(position: Int, type: Int) {
        val bundle = Bundle()
        bundle.putSerializable(Constant.IntentKey.NEWS_LIST, newsAdapter?.localNewsList)
        val intent = Intent(this, DetailNewsActivity::class.java)
        intent.putExtra(Constant.IntentKey.NEWS_POSITION, position)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    /*  override fun shareSurvey(survey_id: Int, survey_question: String) {

          if (MoliticsAppPermission.checkWritePermission()) {
              BranchShareClass.generateShareLink(this, this, survey_question, "", Constant.ShareCampaign.SURVEY,
                      Constant.ShareLinkTag.SURVEY, survey_id.toString(), Constant.ShareLinkTag.NEWS)

          } else {
              MoliticsAppPermission.requestStoragePermission(this)
          }
      }
  */
    override fun onGenerateShareLink(full_txt: String?, url: String?) {
        ShareScreenShot.takeScreenshot(this, getString(R.string.media_of_politics), url)

    }


    override fun onVideoClick(position: Int, video_url: String?) {
        ///   TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadMore(totalItem: Int) {
        /// TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun recyclerChangeListener() {
        var pastVisiblesItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int

        channelNewsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = channelLayoutManager.childCount
                    totalItemCount = channelLayoutManager.itemCount
                    pastVisiblesItems = channelLayoutManager.findFirstVisibleItemPosition()

                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false
                            channelBankViewModel.getChannelsNewsRequest()
                        }
                    }
                }
            }
        })
    }
}