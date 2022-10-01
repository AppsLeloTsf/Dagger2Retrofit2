package com.molitics.molitician.ui.dashboard.channels

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.molitics.molitician.BR
import com.molitics.molitician.BaseActivity
import com.molitics.molitician.R
import com.molitics.molitician.databinding.ActivityChannelListBinding
import com.molitics.molitician.ui.dashboard.channels.adapter.ChannelListAdapter
import com.molitics.molitician.ui.dashboard.channels.viewModel.ChannelListViewModel
import com.molitics.molitician.ui.dashboard.hotTopics.HotTopicAdapter
import com.molitics.molitician.util.Constant
import kotlinx.android.synthetic.main.activity_channel_list.*


import javax.inject.Inject
import androidx.recyclerview.widget.DividerItemDecoration
import com.molitics.molitician.util.Constant.MOLITICS_MEDIA
import com.molitics.molitician.util.Constant.SEARCH_DELAY


class ChannelListActivity : BaseActivity<ActivityChannelListBinding, ChannelListViewModel>(), HotTopicAdapter.HotTopicListener {


    @Inject
    lateinit var channelListViewModel: ChannelListViewModel

    @Inject
    lateinit var llayoutManager: LinearLayoutManager

    lateinit var listAdapter: ChannelListAdapter

    var search = ""

    // for pagination
    private var loading = true
    var pastVisiblesItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0

    // delay in search
    internal var handler = Handler(Looper.getMainLooper() /*UI thread*/)
    lateinit var workRunnable: Runnable

    // move this into module class-> using qualifier tag so that can define two LinearLayoutManager
    val channelLayoutManager = LinearLayoutManager(this@ChannelListActivity, RecyclerView.VERTICAL, false)

    override fun getBindingVariable(): Int = BR.channelViewModel

    override fun getLayoutId(): Int = R.layout.activity_channel_list

    override fun getViewModel(): ChannelListViewModel = channelListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setListAdapter()
        setDataListener()
        channelListViewModel.getChannelsListRequest(search)
        setSupportActionBar(channelToolBar)
        showHeader(channelToolBar, true, getString(R.string.news_channels))
        recyclerChangeListener()
        searchChannel()

    }

    private fun setListAdapter() {
        listAdapter = ChannelListAdapter(this@ChannelListActivity)
        channelRecyclerView.apply {
            adapter = listAdapter
            layoutManager = channelLayoutManager
            addItemDecoration(DividerItemDecoration(context, channelLayoutManager.orientation))
        }
    }

    private fun setDataListener() {
        channelListViewModel.channelList.observe(this, Observer {
            loading = true
            listAdapter.addHotTopics(it)
        })
    }

    override fun onHotTopicClick(position: Int, id: Int, tag: String, sourceLogo: String?) {
        val intent = Intent(this, ChannelBankActivity::class.java)
        intent.putExtra(Constant.IntentKey.CHANNEL_ID, id)
        intent.putExtra(Constant.IntentKey.SOURCE, tag)
        intent.putExtra(Constant.IntentKey.SOURCE_IMAGE, sourceLogo)
        startActivity(intent)
    }

    override fun onFollowChannelClick(id: Int, action: Int) {
        viewModel.followRequest(id, action)
    }

    private fun searchChannel() {

        channelSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String): Boolean {

                if (::workRunnable.isInitialized) {
                    handler.removeCallbacks(workRunnable)
                }
                workRunnable = Runnable {
                    listAdapter.clearData()
                    search = text
                    channelListViewModel.pageNo = 1
                    channelListViewModel.getChannelsListRequest(search)
                }
                handler.postDelayed(workRunnable, SEARCH_DELAY /*delay*/)

                return true
            }
        })
    }

    private fun recyclerChangeListener() {
        channelRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0)
                //check for scroll down
                {
                    visibleItemCount = channelLayoutManager.childCount
                    totalItemCount = channelLayoutManager.itemCount
                    pastVisiblesItems = channelLayoutManager.findFirstVisibleItemPosition()

                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false
                            channelListViewModel.getChannelsListRequest("")
                        }
                    }
                }
            }
        })
    }
}