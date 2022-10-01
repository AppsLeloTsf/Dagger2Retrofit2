package com.molitics.molitician.ui.dashboard.register.forgotPassword

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.molitics.molitician.BR
import com.molitics.molitician.BaseFragment
import com.molitics.molitician.R
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.databinding.FragmentForgotPasswordResetBinding
import com.molitics.molitician.ui.dashboard.ActivityFragment
import com.molitics.molitician.ui.dashboard.register.SignInFragment
import com.molitics.molitician.ui.dashboard.register.forgotPassword.viewModel.ForgotPasswordViewModel
import com.molitics.molitician.ui.dashboard.register.register.RegisterNavigation
import com.molitics.molitician.util.Constant
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import javax.inject.Inject

class ForgotPasswordResetFragment : BaseFragment<FragmentForgotPasswordResetBinding, ForgotPasswordViewModel>(),
    RegisterNavigation {

    @Inject
    lateinit var factory: MyViewModelFactory<ForgotPasswordViewModel>

    private val forgotViewModel: ForgotPasswordViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[ForgotPasswordViewModel::class.java]
    }

    override fun getBindingVariable(): Int = BR.forgotViewModel

    override fun getLayoutId(): Int = R.layout.fragment_forgot_password_reset

    override fun getViewModel(): ForgotPasswordViewModel = forgotViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClickListener()
        forgotViewModel.setScreenType(Constant.From.FORGET_PASSWORD)
        forgotViewModel.navigator = this
    }

    private fun handleClickListener() {
        backButton.setOnClickListener {
            (activity as ActivityFragment).goBack()
        }
    }

    companion object {
        fun getInstance(): ForgotPasswordResetFragment {
            return ForgotPasswordResetFragment()
        }
    }

    override fun navigateToOtp() {
        (activity as ActivityFragment).replaceFragment(SignInFragment.getInstance(), null, false, false)
    }
}