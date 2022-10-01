package com.molitics.molitician.ui.dashboard.changePassword.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.molitics.molitician.BaseViewModel
import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.R
import com.molitics.molitician.httpapi.RetrofitRestClient
import com.molitics.molitician.httpapi.awaitResult
import com.molitics.molitician.ui.dashboard.changePassword.ChangePasswordInterface
import com.molitics.molitician.ui.dashboard.changePassword.model.ChangePasswordRequestModel
import com.molitics.molitician.ui.dashboard.changePassword.model.UserChangePassword
import com.molitics.molitician.util.Util
import com.molitics.molitician.util.resultFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class ChangePasswordViewModel(private val baseApplication: MolticsApplication) :
    BaseViewModel<ChangePasswordInterface>() {

    val currentPassword = ObservableField("")

    val newPassword = ObservableField("")

    val reNewPassword = ObservableField("")

    fun changePasswordClick() {
        if (validateData()) {
            setIsLoading(true)
            viewModelScope.launch(coroutinException) {
                val data = changePasswordApi(
                    ChangePasswordRequestModel(
                        UserChangePassword(
                            currentPassword.get(),
                            newPassword.get(),
                            reNewPassword.get()
                        )
                    )
                ).resultFactory(baseApplication)
                setIsLoading(false)
                data?.let {
                    navigator.onPasswordChanged()
                }
            }
        }
    }

    private fun validateData(): Boolean {
        return when {
            currentPassword.get().isNullOrEmpty() -> {
                Util.showToast(baseApplication, baseApplication.getString(R.string.cannot_empty))
                false
            }
            newPassword.get().isNullOrEmpty() -> {
                Util.showToast(baseApplication, baseApplication.getString(R.string.cannot_empty))
                false
            }
            reNewPassword.get().isNullOrEmpty() -> {
                Util.showToast(baseApplication, baseApplication.getString(R.string.cannot_empty))
                false
            }

            else -> true
        }
    }

    private suspend fun changePasswordApi(changePasswordViewModel: ChangePasswordRequestModel) =
        RetrofitRestClient.instance.changePassword(changePasswordViewModel).awaitResult()

    private val coroutinException = CoroutineExceptionHandler { coroutineContext, throwable ->
        setIsLoading(false)
        Util.showLog("coroutinException", throwable.message)
    }
}