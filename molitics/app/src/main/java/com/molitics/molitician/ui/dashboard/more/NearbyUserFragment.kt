package com.molitics.molitician.ui.dashboard.more

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.molitics.molitician.BR
import com.molitics.molitician.BaseFragment
import com.molitics.molitician.R
import com.molitics.molitician.databinding.ActivityNearbyUserBinding
import com.molitics.molitician.ui.dashboard.more.adapter.ContactListAdapter
import com.molitics.molitician.ui.dashboard.more.interfacess.NearbyUserNavigagtion
import com.molitics.molitician.ui.dashboard.more.viewModel.NearByUserViewModel
import kotlinx.android.synthetic.main.activity_nearby_user.*
import javax.inject.Inject

class NearbyUserFragment : BaseFragment<ActivityNearbyUserBinding, NearByUserViewModel>(), NearbyUserNavigagtion {

    private var loading = true
    var pastVisiblesItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var type: Int = 0
    var search: String = ""

    internal var handler = Handler(Looper.getMainLooper() /*UI thread*/)
    lateinit var workRunnable: Runnable

    @Inject
    lateinit var nearByUserModel: NearByUserViewModel

    @Inject
    lateinit var contactAdapter: ContactListAdapter

    @Inject
    internal lateinit var mLayoutManager: GridLayoutManager

    override fun getBindingVariable(): Int {
        return BR.nearByUser
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_nearby_user
    }

    override fun getViewModel(): NearByUserViewModel {
        return nearByUserModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nearByUserModel.navigator = this
        viewModel.getContactFromServer(1, search, type)
        subScribeContactData()
        bindAdapter()
        recyclerChangeListener()
        // getToolBar()
    }


    private fun subScribeContactData() {
        viewModel.contactLiveData.observe(this,
                Observer {
                    loading = true
                    viewModel.addContactToList(it)
                })
    }

    private fun bindAdapter() {

        mLayoutManager.orientation = RecyclerView.VERTICAL

        //// eventAdapter = EventAdapter(activity!!, this)
        contactRecycler.layoutManager = mLayoutManager
        contactRecycler.adapter = contactAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_leader_view, menu)
        var searchView: SearchView?

        val sItem: MenuItem = menu.findItem(R.id.search_menu)
        val searchItem = menu.findItem(R.id.search_menu)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = searchItem.actionView as SearchView
        // searchView.setSearchableInfo(searchManager.getSearchableInfo(this.componentName))
        searchView.setIconifiedByDefault(true)
        sItem.actionView = searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String): Boolean {

                if (::workRunnable.isInitialized) {
                    handler.removeCallbacks(workRunnable)
                }
                workRunnable = Runnable {
                    nearByUserModel.pageCount = 1
                    search = text
                    nearByUserModel.getContactFromServer(nearByUserModel.pageCount, search, type)
                }
                handler.postDelayed(workRunnable, 1000 /*delay*/)

                return true
            }
        })
        searchView.setOnCloseListener {
            nearByUserModel.pageCount = 1
            search = ""
            nearByUserModel.getContactFromServer(nearByUserModel.pageCount, search, type)
            true
        }
    }


    override fun onContactSyncComplete() {
    }

    private fun recyclerChangeListener() {
        contactRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                            nearByUserModel.getContactFromServer(nearByUserModel.pageCount, search, type)
                        }
                    }
                }
            }
        })
    }
}