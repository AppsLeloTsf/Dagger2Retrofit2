package com.molitics.molitician.ui.dashboard.register

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.molitics.molitician.BR
import com.molitics.molitician.BaseFragment
import com.molitics.molitician.R
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.databinding.FragmentSignInBinding
import com.molitics.molitician.ui.dashboard.ActivityFragment
import com.molitics.molitician.ui.dashboard.register.otp.OtpFragment
import com.molitics.molitician.ui.dashboard.register.password.PasswordFragment
import com.molitics.molitician.ui.dashboard.register.viewModel.SignInViewModel
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.nullify
import kotlinx.android.synthetic.main.fragment_sign_in.*
import javax.inject.Inject

class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel>(), SignInNavigation {

    @Inject
    lateinit var factory: MyViewModelFactory<SignInViewModel>

    private val signUpViewModel: SignInViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[SignInViewModel::class.java]
    }

    override fun getBindingVariable(): Int = BR.signInViewModel

    override fun getLayoutId(): Int = R.layout.fragment_sign_in

    override fun getViewModel(): SignInViewModel = signUpViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpViewModel.navigator = this
        handleClickListener()
        setObserver()
    }

    private fun handleClickListener() {
        registerTV.setOnClickListener {
            (activity as ActivityFragment).replaceFragment(SignUpFragment.getInstance(), null, false, false)
        }
        backButton.setOnClickListener {
            (activity as ActivityFragment).goBack()
        }
    }

    private fun setObserver() {
        signUpViewModel.contactResponse.observe(this, Observer {
            it?.let {
                navigateToOtp(it.mobile, it.auth_key)
                signUpViewModel.contactResponse.nullify()
            }
        })
    }

    companion object {

        fun getInstance(): SignInFragment {
            val instance = SignInFragment()
            val mBundle = Bundle()
            instance.arguments = mBundle
            return instance
        }
    }

    override fun navigateToPassword() {
        (activity as ActivityFragment).replaceFragment(PasswordFragment.getInstance(), null, true, false)
    }

    override fun navigateToOtp(contactNumber: String?, authKey: String?) {
        (activity as ActivityFragment).replaceFragment(OtpFragment.getInstance(contactNumber, Constant.From.SIGN_IN, authKey), null, true, false)
    }
}