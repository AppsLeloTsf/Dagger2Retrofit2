package com.molitics.molitician.ui.dashboard.changePassword

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.molitics.molitician.BaseFragment
import com.molitics.molitician.R
import com.molitics.molitician.BR
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.databinding.FragmentChangePasswordBinding
import com.molitics.molitician.ui.dashboard.changePassword.viewmodel.ChangePasswordViewModel
import com.molitics.molitician.util.Util
import javax.inject.Inject

class ChangePasswordFragment :
    BaseFragment<FragmentChangePasswordBinding, ChangePasswordViewModel>(),
    ChangePasswordInterface {

    @Inject
    lateinit var factory: MyViewModelFactory<ChangePasswordViewModel>

    private val changePasswordViewModel: ChangePasswordViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[ChangePasswordViewModel::class.java]
    }

    override fun getBindingVariable(): Int = BR.changePasswordViewModel

    override fun getLayoutId(): Int = R.layout.fragment_change_password

    override fun getViewModel(): ChangePasswordViewModel = changePasswordViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changePasswordViewModel.navigator = this
    }

    companion object {

        fun getInstance(): ChangePasswordFragment {
            return ChangePasswordFragment()
        }
    }

    override fun onPasswordChanged() {
        Util.showToast(requireContext(), getString(R.string.password_has_changed))
        requireActivity().finish()
    }
}