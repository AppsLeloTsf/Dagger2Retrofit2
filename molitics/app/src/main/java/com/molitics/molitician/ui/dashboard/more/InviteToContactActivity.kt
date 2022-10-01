package com.molitics.molitician.ui.dashboard.more

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.molitics.molitician.BR
import com.molitics.molitician.BaseActivity
import com.molitics.molitician.R
import com.molitics.molitician.databinding.ActivityInviteToContactBinding
import com.molitics.molitician.ui.dashboard.more.adapter.ContactListAdapter
import com.molitics.molitician.ui.dashboard.more.interfacess.InviteToContactNavigation
import com.molitics.molitician.ui.dashboard.more.viewModel.InviteToContactViewModel
import com.molitics.molitician.util.Constant
import kotlinx.android.synthetic.main.activity_invite_to_contact.*
import kotlinx.android.synthetic.main.activity_invite_to_contact.contactRecycler
import kotlinx.android.synthetic.main.activity_invite_to_contact.toolbar
import kotlinx.android.synthetic.main.activity_nearby_user.*
import javax.inject.Inject

class InviteToContactActivity : BaseActivity<ActivityInviteToContactBinding, InviteToContactViewModel>(), InviteToContactNavigation {

    private var loading = true
    var pastVisiblesItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var search: String = ""


    @Inject
    lateinit var inviteToContact: InviteToContactViewModel

    @Inject
    lateinit var contactAdapter: ContactListAdapter

    @Inject
    internal lateinit var mLayoutManager: GridLayoutManager

    override fun getBindingVariable(): Int {
        return BR.inviteToContact
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_invite_to_contact
    }

    override fun getViewModel(): InviteToContactViewModel {
        return inviteToContact
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inviteToContact.navigator = this
        setActionBar()
        if (intent!!.hasExtra(Constant.IntentKey.SYNC_CONTACT)) {
            inviteToContact.syncContactWithServer(this)
        } else {
            viewModel.getContactFromServer(1, search)
        }
        subScribeContactData()
        bindAdapter()
        recyclerChangeListener()
    }

    private fun setActionBar() {
        setSupportActionBar(toolbar)
        showHeader(toolbar, true, getString(R.string.invite_contact))
        toolbar.setNavigationOnClickListener { goBack() }
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

    override fun onContactSyncComplete() {
    }

    internal var handler = Handler(Looper.getMainLooper() /*UI thread*/)
    lateinit var workRunnable: Runnable

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_invite_to_contact, menu)

        val sItem: MenuItem = menu!!.findItem(R.id.search_menu)
        val searchItem = menu.findItem(R.id.search_menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.componentName))
        searchView.setIconifiedByDefault(true)
        sItem.actionView = searchView

        /*      if (!search_text.isEmpty()) {
                  MenuItemCompat.expandActionView(menu.findItem(R.id.search_menu))
                  searchView.setQuery(search_text, false)
              }*/
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String): Boolean {

                if (::workRunnable.isInitialized) {
                    handler.removeCallbacks(workRunnable)
                }
                workRunnable = Runnable {
                    inviteToContact.pageCount = 1
                    search = text
                    inviteToContact.getContactFromServer(inviteToContact.pageCount, search)
                }
                handler.postDelayed(workRunnable, 1000 /*delay*/)

                return true
            }
        })
        searchView.setOnCloseListener {
            inviteToContact.pageCount = 1
            search = ""
            inviteToContact.getContactFromServer(inviteToContact.pageCount, search)
            true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.refresh_menu) {
            inviteToContact.pageCount = 1
            inviteToContact.syncContactWithServer(this)
        }
        return super.onOptionsItemSelected(item)
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
                            inviteToContact.getContactFromServer(inviteToContact.pageCount, search)
                        }
                    }
                }
            }
        })
    }

}