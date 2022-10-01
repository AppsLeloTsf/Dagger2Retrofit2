package com.molitics.molitician.ui.dashboard.more

import android.Manifest.permission.READ_CONTACTS
import android.app.AlertDialog
import android.content.Intent

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.molitics.molitician.BR
import com.molitics.molitician.BaseFragment
import com.molitics.molitician.GoogleAnalytics.GAEventTracking
import com.molitics.molitician.R
import com.molitics.molitician.databinding.FragmentMoreTabBinding
import com.molitics.molitician.ui.dashboard.ActivityFragment
import com.molitics.molitician.ui.dashboard.articles.ArticleActivity
import com.molitics.molitician.ui.dashboard.election.past_election.past_states.PartListView
import com.molitics.molitician.ui.dashboard.more.viewModel.MoreTabViewModel
import com.molitics.molitician.ui.dashboard.newsBookmark.NewsBookMarkView
import com.molitics.molitician.ui.dashboard.notification.NotificationActivity
import com.molitics.molitician.ui.dashboard.survey.SurveyActivity
import com.molitics.molitician.ui.dashboard.termCondition.TermConditionActivity
import com.molitics.molitician.ui.dashboard.video.VideoActivity
import com.molitics.molitician.ui.dashboard.voice.VoiceCreatorProfile
import com.molitics.molitician.util.*
import com.molitics.molitician.util.MoliticsAppPermission.CONTACT_PERMISSION_CODE
import com.molitics.molitician.util.Util.inviteToMolitics
import kotlinx.android.synthetic.main.fragment_more_tab.*
import javax.inject.Inject

class MoreTabFragment : BaseFragment<FragmentMoreTabBinding, MoreTabViewModel>() {

    @Inject
    lateinit var moreTabViewModel: MoreTabViewModel

    override fun getBindingVariable(): Int {

        return BR.moreTabViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_more_tab
    }

    override fun getViewModel(): MoreTabViewModel {
        return moreTabViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getToolBar()
        screenClick()
        checkNotification()
    }

    private fun getToolBar() {
        val toolbar = requireActivity().findViewById<View>(R.id.toolbar) as Toolbar
        val centerToolbarRl = toolbar.findViewById<View>(R.id.center_toolbar_rl) as RelativeLayout
        centerToolbarRl.removeAllViews()

        val child = layoutInflater.inflate(R.layout.common_txt_view, null)
        centerToolbarRl.addView(child)

        val headerText = child.findViewById<View>(R.id.state_name) as TextView
        headerText.setText(R.string.more)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_check, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun screenClick() {
        rl_profile.setOnClickListener {
            val mIntent = Intent(context, VoiceCreatorProfile::class.java)
            startActivity(mIntent)
        }
  /*      rl_nearby.setOnClickListener {
            val mIntent = Intent(context, ActivityFragment::class.java)
            mIntent.putExtra(Constant.INTENT_FROM, Constant.From.FROM_NEARBY_USER)
            startActivity(mIntent)
        }*/
  /*      rl_invite_contact.setOnClickListener {
            if (MoliticsAppPermission.contactPermission(activity)) {
                val mIntent = Intent(context, InviteToContactActivity::class.java)
                startActivity(mIntent)
            } else {
                requestPermissions(arrayOf<String>(READ_CONTACTS), CONTACT_PERMISSION_CODE)
            }
        }*/
        rl_bookmark.setOnClickListener {
            startActivity(Intent(context, NewsBookMarkView::class.java))
        }
        rl_favourite.setOnClickListener {

            val mIntent = Intent(context, PartListView::class.java)
            mIntent.putExtra(Constant.IntentKey.ELECTION_ID, 0)

            mIntent.putExtra(Constant.IntentKey.FROM, Constant.From.FROM_PROFILE)
            startActivity(mIntent)

            setGAEvent(Constant.GoogleAnalyticsKey.SIDE_DRAWER, Constant.GoogleAnalyticsKey.LEADER_FOLLOWING_CLICK)
        }
        rl_survey.setOnClickListener {
            PrefUtil.putInt(Constant.PreferenceKey.NOTIFICATION_SURVEY_COUNT, 0)
            val mIntent = Intent(context, SurveyActivity::class.java)
            startActivityForResult(mIntent, Constant.ActivityRequestKey.SURVEY_KEY)
            setGAEvent(Constant.GoogleAnalyticsKey.SIDE_DRAWER, Constant.GoogleAnalyticsKey.SURVEY_CLICK)
        }
   /*     rl_election.setOnClickListener {
            val electionIntent = Intent(context, ActivityFragment::class.java)
            electionIntent.putExtra(Constant.INTENT_FROM, Constant.From.FROM_ELECTION_RESULT)
            startActivity(electionIntent)
            setGAEvent(Constant.GoogleAnalyticsKey.SIDE_DRAWER, Constant.GoogleAnalyticsKey.ELECTION_RESULT_CLICK)
        }*/
        /*rl_plus.setOnClickListener { }*/
        invite_to_molitics.setOnClickListener {
            inviteToMolitics(Constant.MOLITICS_BRANCHLINK)
        }
        rl_notification.setOnClickListener {
            val mIntent = Intent(context, NotificationActivity::class.java)
            mIntent.putExtra(Constant.IntentKey.NOTIFICATION_TITLE, getString(R.string.notification_txt))
            startActivityForResult(mIntent, Constant.ActivityRequestKey.NOTIFICATION_KEY)
        }
        rl_term.setOnClickListener {
            val mIntent = Intent(context, TermConditionActivity::class.java)
            mIntent.putExtra(Constant.IntentKey.TOOL_BAR, R.string.terms_condition)
            mIntent.putExtra(Constant.IntentKey.URL, getString(R.string.term_condition_url))
            startActivity(mIntent)
        }
        rl_logout.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(R.string.logout_confirm_txt)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes)) { _, _ -> Util.logout(activity) }
                    .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                        //  Action for 'NO' Button
                        dialog.cancel()
                    }
            val alert = builder.create()
            alert.setTitle(getString(R.string.log_out))
            alert.show()
        }
        rl_rate.setOnClickListener {
            inviteToMolitics(context, requireContext().packageName)
        }
        switch_button.setOnClickListener {
            if (PrefUtil.getInt(Constant.PreferenceKey.NOTIFICATION_ACTIVE) == 1) {
                PrefUtil.putInt(Constant.PreferenceKey.NOTIFICATION_ACTIVE, 0)
            } else {
                PrefUtil.putInt(Constant.PreferenceKey.NOTIFICATION_ACTIVE, 1)
            }
        }
/*        moliticsPlusView.setOnClickListener {
            val mIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.molitics.net/"))
            startActivity(mIntent)
        }
        rl_article.setOnClickListener { startActivity(Intent(context, ArticleActivity::class.java)) }
        rl_video.setOnClickListener { startActivity(Intent(context, VideoActivity::class.java)) }*/
    }

    private fun checkNotification() {
        switch_button.isChecked = PrefUtil.getInt(Constant.PreferenceKey.NOTIFICATION_ACTIVE) == 1
    }

    private fun setGAEvent(category: String, action: String) {
        GAEventTracking.googleEventTracker(activity, category, action)
    }

    fun inviteToMolitics(link: String?) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, link)
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, getString(R.string.share_to)))
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CONTACT_PERMISSION_CODE) {
            for (i in permissions.indices) {
                val permission = permissions[i]
                val grantResult = grantResults[i]
                if (permission == READ_CONTACTS) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        val mIntent = Intent(context, InviteToContactActivity::class.java)
                        mIntent.putExtra(Constant.IntentKey.SYNC_CONTACT, true)
                        startActivity(mIntent)
                    } else {
                        Util.customToast(context, getString(R.string.cannt_sync_contact))
                    }
                }
            }
        }
    }

}
