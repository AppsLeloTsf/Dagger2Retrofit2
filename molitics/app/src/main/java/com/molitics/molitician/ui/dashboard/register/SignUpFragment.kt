package com.molitics.molitician.ui.dashboard.register

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.molitics.molitician.BR
import com.molitics.molitician.BaseFragment
import com.molitics.molitician.R
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.databinding.FragmentSignupBinding
import com.molitics.molitician.ui.dashboard.ActivityFragment
import com.molitics.molitician.ui.dashboard.register.otp.OtpFragment
import com.molitics.molitician.ui.dashboard.register.register.RegisterFragment
import com.molitics.molitician.ui.dashboard.register.register.RegisterNavigation
import com.molitics.molitician.ui.dashboard.register.register.viewModel.RegisterViewModel
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.Util.openTermConditionActivity
import kotlinx.android.synthetic.main.fragment_signup.*
import javax.inject.Inject

class SignUpFragment : BaseFragment<FragmentSignupBinding, RegisterViewModel>(), RegisterNavigation {

    @Inject
    lateinit var factory: MyViewModelFactory<RegisterViewModel>

    private val signUpViewModel: RegisterViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[RegisterViewModel::class.java]
    }

    override fun getBindingVariable(): Int = BR.signUpViewModel

    override fun getLayoutId(): Int = R.layout.fragment_signup

    override fun getViewModel(): RegisterViewModel = signUpViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpViewModel.navigator = this
        handleClickListener()
        setTnCPrivacyLinkClickable()
    }

    private fun handleClickListener() {
        backButton.setOnClickListener {
            (activity as ActivityFragment).goBack()
        }
        signInTV.setOnClickListener {
            (activity as ActivityFragment).replaceFragment(SignInFragment.getInstance(), null, false, false)
        }
    }

    /* handle term and condition view with underline and open web view on click */
    private fun setTnCPrivacyLinkClickable() {
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                openTermConditionActivity(requireContext(), getString(R.string.term_condition), getString(R.string.term_condition_url))
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
        val clickableSpan1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                openTermConditionActivity(requireContext(), getString(R.string.privacy_policy), getString(R.string.term_condition_url))
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }

        val prefixTerm: String = getString(R.string.by_registering)
        val termCondition: String = getString(R.string.term_condition)
        val and: String = getString(R.string.and)
        val privacyPolicy: String = getString(R.string.privacy_policy)
        val clickableSpan1Length = prefixTerm.length + termCondition.length + and.length

        val ssb = SpannableStringBuilder(prefixTerm).append(" ")
        ssb.append(termCondition).append(" ").append(and).append(" ").append(privacyPolicy)
        ssb.setSpan(
                clickableSpan,
                prefixTerm.length + 1,
                prefixTerm.length + 1 + termCondition.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.setSpan(
                clickableSpan1,
                clickableSpan1Length + 3,
                clickableSpan1Length + privacyPolicy.length + 3,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        ssb.setSpan(ForegroundColorSpan(Color.BLUE), prefixTerm.length + 1,
                prefixTerm.length + 1 + termCondition.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(ForegroundColorSpan(Color.BLUE), clickableSpan1Length + 3,
                clickableSpan1Length + privacyPolicy.length + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        termConditionView.text = ssb
        termConditionView.movementMethod = LinkMovementMethod.getInstance()
    }

    companion object {

        fun getInstance(): SignUpFragment {
            val instance = SignUpFragment()
            val mBundle = Bundle()
            instance.arguments = mBundle
            return instance
        }
    }

    override fun navigateToRegister() {
        (activity as ActivityFragment).replaceFragment(RegisterFragment.getInstance(), null, false, false)
    }

    override fun navigateToOtp(contactNumber: String?, authKey: String?) {
        (activity as ActivityFragment).replaceFragment(OtpFragment.getInstance(contactNumber, Constant.From.SIGN_UP_FRAGMENT, authKey), null, true, false)
    }
}