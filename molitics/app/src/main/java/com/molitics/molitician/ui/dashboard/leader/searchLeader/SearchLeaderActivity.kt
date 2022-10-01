package com.molitics.molitician.ui.dashboard.leader.searchLeader

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import butterknife.ButterKnife
import com.molitics.molitician.R
import com.molitics.molitician.ui.dashboard.leader.LeaderView
import com.molitics.molitician.util.Constant.From.SEARCH_LEADER
import kotlinx.android.synthetic.main.activity_user_profile_edit.*

class SearchLeaderActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search_leader)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        // showHeader(true, getString(R.string.my_profile))
        replaceFragment(LeaderView.getInstance(1, SEARCH_LEADER), addToBackStack = false, anim = false)
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_blank, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean, anim: Boolean) {
        val tag = fragment.javaClass.simpleName
        val ft = supportFragmentManager.beginTransaction()

        ft.replace(R.id.home_container, fragment, tag)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragment.retainInstance = true
        if (addToBackStack)
            ft.addToBackStack(tag)
        try {
            ft.commit()
        } catch (ex: Exception) {
            ex.printStackTrace()
            ft.commitAllowingStateLoss()
        }

    }

}