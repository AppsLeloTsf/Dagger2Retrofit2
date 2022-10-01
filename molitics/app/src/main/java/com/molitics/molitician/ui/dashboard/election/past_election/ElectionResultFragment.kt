package com.molitics.molitician.ui.dashboard.election.past_election

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.widget.SearchView

import com.molitics.molitician.R
import com.molitics.molitician.error.ApiException
import com.molitics.molitician.error.ServerException
import com.molitics.molitician.interfaces.ViewRefreshListener
import com.molitics.molitician.model.APIResponse
import com.molitics.molitician.ui.dashboard.constantData.RecyclerTouchWithType
import com.molitics.molitician.ui.dashboard.election.past_election.adapter.PastElectionAdapter
import com.molitics.molitician.ui.dashboard.election.past_election.past_states.ElectionResultDetailActivity
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.DialogClass
import com.molitics.molitician.util.NetworkUtil
import com.molitics.molitician.util.Util
import kotlinx.android.synthetic.main.fragment_past_election.*

/**
 * Created by rahul on 1/9/2017.
 */

class ElectionResultFragment : Fragment(), RecyclerTouchWithType, PastElectionPresenter.PastElectionView, ViewRefreshListener, PastElectionAdapter.OnLoadMoreResult {

    private var electionHandler: PastElectionHandler? = null
    private var pastElectionAdapter: PastElectionAdapter? = null
    private var count = 0
    private var loadMore = false
    private var newsApiCall = true

    internal var handler = Handler(Looper.getMainLooper() /*UI thread*/)
    lateinit var workRunnable: Runnable
    var search = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_past_election, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        electionHandler = PastElectionHandler(this)
        handleUi()
        searchChannel()
    }

    private fun handleUi() {
        count = 0
        pastElectionAdapter = PastElectionAdapter(context, this, this)
        val layoutManager = GridLayoutManager(context, 2)
        state_recycler.layoutManager = layoutManager
        state_recycler.adapter = pastElectionAdapter

        if (NetworkUtil.isNetworkConnected(context!!)) {
            electionHandler!!.getPastElection(count, search)
            loading_grid.visibility = View.VISIBLE
        } else
            Util.addNetworkNotFoundView(context, RL_handler, this)
    }

    override fun onPastElectionResponse(apiResponse: APIResponse) {
        state_recycler!!.visibility = View.VISIBLE
        loading_grid.visibility = View.GONE
        RL_handler.visibility = View.GONE
        val allPastElectionLists = apiResponse.data.allPastElectionLists
        if (count == 0) {
            pastElectionAdapter!!.clearStateList()
        }
        pastElectionAdapter!!.addStateList(allPastElectionLists)
        pageNoHandler(allPastElectionLists.size)
    }

    private fun pageNoHandler(size: Int) {
        newsApiCall = true
        loadMore = size > 9
    }

    override fun onPastElectionApiException(apiException: ApiException) {
        loading_grid.visibility = View.GONE
        loading_grid.visibility = View.GONE

        Util.validateError(context, apiException, RL_handler,
                this, pastElectionAdapter!!.electionListSize)

    }

    override fun onPastElectionServerError(serverException: ServerException) {
        loading_grid!!.visibility = View.GONE
        loading_grid!!.visibility = View.GONE
        DialogClass.showAlert(context, getString(R.string.something_went_wrong))
    }

    override fun onRefereshClick() {
        loading_grid!!.visibility = View.VISIBLE
        electionHandler!!.getPastElection(count, search)
    }

    override fun onNetworkAvailable() {
        onRefereshClick()
    }

    override fun onLoadMore(totalItem: Int) {
        count = totalItem
        if (loadMore && newsApiCall) {
            newsApiCall = false
            loading_grid!!.visibility = View.VISIBLE
            electionHandler!!.getPastElection(count, search)
        }
    }

    override fun onVerticalRecycler(position: Int, type: Int) {
        val mIntent = Intent(context, ElectionResultDetailActivity::class.java)
        mIntent.putExtra(Constant.IntentKey.ELECTION_ID, position)
        mIntent.putExtra(Constant.IntentKey.PAST_STATE_ID, type)
        startActivity(mIntent)
    }

    override fun onCloseClick() {
    }

    private fun searchChannel() {
        stateSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String): Boolean {

                if (::workRunnable.isInitialized) {
                    handler.removeCallbacks(workRunnable)
                }
                workRunnable = Runnable {
                    count = 0
                    search = text
                    pastElectionAdapter?.clearStateList()
                    electionHandler!!.getPastElection(count, search)
                    //channelListViewModel.getChannelsListRequest(search)
                }
                handler.postDelayed(workRunnable, Constant.SEARCH_DELAY /*delay*/)

                return true
            }
        })
    }
}
