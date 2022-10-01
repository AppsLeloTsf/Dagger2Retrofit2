package com.molitics.molitician.ui.dashboard.register.password

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.molitics.molitician.BR
import com.molitics.molitician.BaseFragment
import com.molitics.molitician.R
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.databinding.FragmentPasswordBinding
import com.molitics.molitician.ui.dashboard.ActivityFragment
import com.molitics.molitician.ui.dashboard.DashBoardActivity
import com.molitics.molitician.ui.dashboard.register.forgotPassword.ForgotPasswordFragment
import com.molitics.molitician.ui.dashboard.register.viewModel.SignInViewModel
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.PrefUtil
import com.molitics.molitician.util.Util
import kotlinx.android.synthetic.main.fragment_password.*
import javax.inject.Inject

class PasswordFragment : BaseFragment<FragmentPasswordBinding, SignInViewModel>() {

    @Inject
    lateinit var factory: MyViewModelFactory<SignInViewModel>

    private val passwordViewModel: SignInViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[SignInViewModel::class.java]
    }

    override fun getBindingVariable(): Int = BR.passwordViewModel

    override fun getLayoutId(): Int = R.layout.fragment_password

    override fun getViewModel(): SignInViewModel = passwordViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        handleClickListener()
    }

    private fun handleClickListener() {
        forgotPassword.setOnClickListener {
            (activity as ActivityFragment).replaceFragment(ForgotPasswordFragment.getInstance(), null, true, false)
        }
        backButton.setOnClickListener {
            (activity as ActivityFragment).goBack()
        }
    }

    private fun setObserver() {
        passwordViewModel.contactResponse.observe(this, Observer {
            it?.let {
                Util.saveNewUserInfo(it)
                startActivity(Intent(requireContext(), DashBoardActivity::class.java))
                requireActivity().finish()
            }
        })
    }

    companion object {

        fun getInstance(): PasswordFragment {
            val instance = PasswordFragment()
            val mBundle = Bundle()
            instance.arguments = mBundle
            return instance
        }
    }
}