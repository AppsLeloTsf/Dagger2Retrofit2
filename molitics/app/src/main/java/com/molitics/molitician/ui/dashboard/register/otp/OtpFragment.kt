package com.molitics.molitician.ui.dashboard.register.otp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.molitics.molitician.BR
import com.molitics.molitician.BaseFragment
import com.molitics.molitician.R
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.databinding.FragmentOtpBinding
import com.molitics.molitician.ui.dashboard.ActivityFragment
import com.molitics.molitician.ui.dashboard.DashBoardActivity
import com.molitics.molitician.ui.dashboard.register.SignInNavigation
import com.molitics.molitician.ui.dashboard.register.forgotPassword.ForgotPasswordFragment
import com.molitics.molitician.ui.dashboard.register.forgotPassword.ForgotPasswordResetFragment
import com.molitics.molitician.ui.dashboard.register.register.RegisterFragment
import com.molitics.molitician.ui.dashboard.register.viewModel.SignInViewModel
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.PrefUtil
import com.molitics.molitician.util.Util
import kotlinx.android.synthetic.main.fragment_otp.*
import javax.inject.Inject

class OtpFragment : BaseFragment<FragmentOtpBinding, SignInViewModel>(), SignInNavigation {

    @Inject
    lateinit var factory: MyViewModelFactory<SignInViewModel>

    private val otpViewModel: SignInViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[SignInViewModel::class.java]
    }

    override fun getBindingVariable(): Int = BR.otpViewModel

    override fun getLayoutId(): Int = R.layout.fragment_otp

    override fun getViewModel(): SignInViewModel = otpViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleArgument()
        handleClickListener()
        handleObserver()
        otpViewModel.navigator = this
    }

    private fun handleObserver() {
        otpViewModel.contactResponse.observe(viewLifecycleOwner, Observer {
            Util.saveNewUserInfo(it)
            startActivity(Intent(requireContext(), DashBoardActivity::class.java))
            requireActivity().finish()
        })
    }

    private fun handleArgument() {
        arguments?.getString(Constant.INTENT_FROM)?.let {
            otpViewModel.setScreenType(it)
        }

        if (arguments?.getString(Constant.IntentKey.NUMBER)?.isNotEmpty() == true) {
            val userContact = arguments?.getString(Constant.IntentKey.NUMBER)
            otpViewModel.contactNumber.set(userContact)
            otpTv.text = "Enter OTP sent to +$userContact"
        }
        if (arguments?.getString(Constant.IntentKey.AUTH_KEY)?.isNotEmpty() == true) {
            otpViewModel.userAuth = arguments?.getString(Constant.IntentKey.AUTH_KEY) ?: ""
        }
    }

    private fun handleClickListener() {
        backButton.setOnClickListener {
            (activity as ActivityFragment).goBack()
        }
    }

    companion object {

        fun getInstance(userContact: String? = null, navigateTo: String, authKey: String?): OtpFragment {
            val instance = OtpFragment()
            val mBundle = Bundle()
            mBundle.putString(Constant.IntentKey.NUMBER, userContact)
            mBundle.putString(Constant.IntentKey.AUTH_KEY, authKey)
            mBundle.putString(Constant.INTENT_FROM, navigateTo)
            instance.arguments = mBundle
            return instance
        }
    }

    private fun navigateTo() {
        when (arguments?.getString(Constant.INTENT_FROM)) {
            Constant.From.SIGN_UP_FRAGMENT -> {
                (activity as ActivityFragment).replaceFragment(RegisterFragment.getInstance(), null, true, false)
            }
            Constant.From.SIGN_IN_FRAGMENT -> {
                startActivity(Intent(requireContext(), DashBoardActivity::class.java))
                requireActivity().finish()
            }
            Constant.From.FORGET_PASSWORD -> {
                (activity as ActivityFragment).replaceFragment(ForgotPasswordResetFragment.getInstance(), null, false, false)
            }
        }
    }

    override fun navigateToPassword() {
    }

    override fun navigateToOtp(contactNumber: String?, authKey: String?) {
        navigateTo()
    }
}