package com.molitics.molitician.ui.dashboard.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.molitics.molitician.BR
import com.molitics.molitician.BaseFragment
import com.molitics.molitician.R
import com.molitics.molitician.databinding.FragmentHomeBinding
import com.molitics.molitician.ui.dashboard.cartoon.CartoonFragment
import com.molitics.molitician.ui.dashboard.election.past_election.ElectionResultFragment
import com.molitics.molitician.ui.dashboard.home.adapter.HomeBrowseAdapter
import com.molitics.molitician.ui.dashboard.home.adapter.TopHomeAdapter
import com.molitics.molitician.ui.dashboard.home.interfacess.HomeBrowserClickListener
import com.molitics.molitician.ui.dashboard.home.viewModel.HomeViewModel
import com.molitics.molitician.ui.dashboard.hotTopics.HotTopicActivity
import com.molitics.molitician.ui.dashboard.liveVideo.LiveVideoFragment
import com.molitics.molitician.ui.dashboard.local.LocalNewsFragment
import com.molitics.molitician.ui.dashboard.more.NearbyUserFragment
import com.molitics.molitician.ui.dashboard.nationalNews.NationalNewsFragment
import com.molitics.molitician.util.Constant.HomeBrowseItem.*
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), HomeBrowserClickListener {


    @Inject
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    @Inject
    lateinit var homeBrowseAdapter: HomeBrowseAdapter

    @Inject
    lateinit var mBrowseLayoutManager: LinearLayoutManager

    @Inject
    lateinit var expandAdapter: TopHomeAdapter

    override fun getBindingVariable(): Int = BR.homeViewModel

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun getViewModel(): HomeViewModel = homeViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBrowserFragment(1)
        setRecyclerView()
        setToolBar()
    }

    private fun setRecyclerView() {

        browseAdapter.apply {
            layoutManager = mBrowseLayoutManager
            adapter = homeBrowseAdapter
        }
        homeBrowseAdapter.setInterface(this)
    }

    override fun onBrowseClick(type: Int) {
        setBrowserFragment(type)
    }

    private fun setToolBar() {
        val toolbar = activity!!.findViewById<Toolbar>(R.id.toolbar)
        val centerToolbarRl = toolbar.findViewById<RelativeLayout>(R.id.center_toolbar_rl)

        if (centerToolbarRl != null) {
            centerToolbarRl.removeAllViews()
            val child = layoutInflater.inflate(R.layout.toolbar_hot_topic, null)
            centerToolbarRl.addView(child)

            val hotTopic = child.findViewById<TextView>(R.id.hot_topic)
            hotTopic.setOnClickListener { v ->
                startActivity(Intent(context, HotTopicActivity::class.java))
                //   setGAEvent(Constant.GoogleAnalyticsKey.TRENDING_NEWS_CLICK);
            }
        }
    }

    private fun setBrowserFragment(type: Int) {
        val childFragment: Fragment = when (type) {
            NATIONAL_type -> NationalNewsFragment()
            LOCAL_NEWS_type -> LocalNewsFragment()
            CARICATURE_TYPE -> CartoonFragment()
            ELECTION_RESULT_TYPE -> ElectionResultFragment()
            NEARBY_USER_TYPE -> NearbyUserFragment()
            LIVE_TV_type -> LiveVideoFragment()

            else -> NationalNewsFragment()
        }
        replaceFragment(childFragment)
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.home_container, fragment).commit()
    }
}