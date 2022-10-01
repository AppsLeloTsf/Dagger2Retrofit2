package com.molitics.molitician.ui.dashboard.verification.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.molitics.molitician.BaseViewModel
import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.R
import com.molitics.molitician.httpapi.RetrofitRestClient
import com.molitics.molitician.httpapi.awaitResult
import com.molitics.molitician.ui.dashboard.BaseNavigation
import com.molitics.molitician.ui.dashboard.verification.VerificationActivityInterface
import com.molitics.molitician.util.CompressImage
import com.molitics.molitician.util.Util
import com.molitics.molitician.util.checkContactNumber
import com.molitics.molitician.util.isEmailValid
import com.molitics.molitician.util.resultFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class VerificationActivityViewModel(val baseApplication: MolticsApplication) :
    BaseViewModel<VerificationActivityInterface>() {

    val contactNumber = ObservableField("")
    val userEmail = ObservableField("")
    val userDescription = ObservableField("")
    val instaUrl = ObservableField("")
    var userStatus = 0
    var citizen = 0
    var docImage = ""

    /** on button click*/
    fun onVerifyClick() {
        if (validateForm()) {
            submitVerificationData(docImage, citizen)
        }
    }

    fun submitVerificationData(docImage: String, citizen: Int) {
        viewModelScope.launch(coroutinException) {
            val data = verificationAPI(docImage, citizen).resultFactory(baseApplication)
            data?.let {
                navigator.onVerificationSubmit()
            }
        }
    }

    private suspend fun verificationAPI(docImage: String, citizen: Int) =
        RetrofitRestClient.instance.userVerificationForm(
            createRequestBody(docImage),
            createRequestBody(citizen.toString()),
            createRequestBody(1.toString()),
            createRequestBody(1.toString()),
            fileIntoMultipart(docImage)
        ).awaitResult()


    fun fileIntoMultipart(docImage: String): MultipartBody.Part? {
        var filePart: MultipartBody.Part? = null
        if (!android.text.TextUtils.isEmpty(docImage)) {
            val mFile = File(CompressImage.compressImage(baseApplication, docImage))
            filePart = MultipartBody.Part.createFormData(
                "doc_file",
                mFile.name, RequestBody.create(MediaType.parse("image/*"), mFile)
            )
        }
        return filePart;
    }

    private fun validateForm(): Boolean {
        if (userDescription.get()!!.isEmpty()) {
            Util.toastShort(
                baseApplication,
                baseApplication.getString(R.string.verification_description_should_not_be_null)
            )
            return false
        }

        if (!isEmailValid(userEmail.get())) {
            Util.toastShort(baseApplication, baseApplication.getString(R.string.email_invalid))
            return false
        }
        return checkContactNumber(baseApplication, contactNumber.get()!!)
    }

    private fun createRequestBody(content: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), content)
    }

    private val coroutinException = CoroutineExceptionHandler { coroutineContext, throwable ->
        Util.showLog("RegisterViewModel", throwable.message)
    }
}