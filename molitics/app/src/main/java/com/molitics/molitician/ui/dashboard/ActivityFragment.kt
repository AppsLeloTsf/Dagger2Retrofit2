package com.molitics.molitician.ui.dashboard

import android.os.Bundle
import com.molitics.molitician.BaseActivity
import com.molitics.molitician.R
import com.molitics.molitician.databinding.ActivityFragmentLayoutBinding
import com.molitics.molitician.ui.dashboard.cartoon.CartoonFragment
import com.molitics.molitician.ui.dashboard.changePassword.ChangePasswordFragment
import com.molitics.molitician.ui.dashboard.election.past_election.ElectionResultFragment
import com.molitics.molitician.ui.dashboard.more.NearbyUserFragment
import com.molitics.molitician.ui.dashboard.register.SignInFragment
import com.molitics.molitician.ui.dashboard.register.SignUpFragment
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.Constant.From.ACTIVITY_CHANGE_PASSWORD_FRAGMENT
import com.molitics.molitician.util.Constant.From.ACTIVITY_SIGN_IN_FRAGMENT
import com.molitics.molitician.util.Constant.From.ACTIVITY_SIGN_UP_FRAGMENT
import com.molitics.molitician.util.Constant.From.FROM_CARICATURE
import com.molitics.molitician.util.Constant.From.FROM_ELECTION_RESULT
import com.molitics.molitician.util.Constant.From.FROM_NEARBY_USER
import com.molitics.molitician.util.PrefUtil
import javax.inject.Inject

class ActivityFragment : BaseActivity<ActivityFragmentLayoutBinding, ActivityFragmentViewModel>() {

    @Inject
    lateinit var activityFragmentViewModel: ActivityFragmentViewModel

    override fun getBindingVariable(): Int = 1

    override fun getLayoutId(): Int = R.layout.activity_fragment_layout

    override fun getViewModel(): ActivityFragmentViewModel = activityFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PrefUtil.putBoolean(Constant.PreferenceKey.IS_FIRST_TIME, true)
        setFragment(intent.getIntExtra(Constant.INTENT_FROM, 0))
    }

    private fun setFragment(type: Int) {
        when (type) {
            FROM_CARICATURE -> replaceFragment(CartoonFragment(), Bundle(), true, false)
            FROM_ELECTION_RESULT -> replaceFragment(ElectionResultFragment(), Bundle(), true, false)
            FROM_NEARBY_USER -> replaceFragment(NearbyUserFragment(), Bundle(), true, false)
            ACTIVITY_SIGN_IN_FRAGMENT -> replaceFragment(SignInFragment.getInstance(), Bundle(), false, false)
            ACTIVITY_SIGN_UP_FRAGMENT -> replaceFragment(SignUpFragment.getInstance(), Bundle(), false, false)
            ACTIVITY_CHANGE_PASSWORD_FRAGMENT -> replaceFragment(ChangePasswordFragment.getInstance(), Bundle(), false, false)
        }
    }
}