package com.molitics.molitician.ui.dashboard.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.facebook.CallbackManager
import com.molitics.molitician.BR
import com.molitics.molitician.R
import com.molitics.molitician.databinding.DialogLoginFragmentBinding
import com.molitics.molitician.ui.dashboard.BaseDialogFragment
import com.molitics.molitician.ui.dashboard.login.viewModel.LoginDialogViewModel
import kotlinx.android.synthetic.main.dialog_login_fragment.*
import javax.inject.Inject
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import com.facebook.GraphRequest
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.molitics.molitician.ui.dashboard.login.interfacess.UserLoginInterface
import com.molitics.molitician.ui.dashboard.login.interfacess.LoginDialogResultCallback
import com.molitics.molitician.util.*


class LoginDialogFragment : BaseDialogFragment<DialogLoginFragmentBinding, LoginDialogViewModel>(), UserLoginInterface {


    val TAG = "LoginDialogFragment"

    @Inject
    lateinit var loginDialogViewModel: LoginDialogViewModel

    private val EMAIL = "email"

    val callbackManager = CallbackManager.Factory.create()
    lateinit var listener: LoginDialogResultCallback

    override fun getBindingVariable(): Int = BR.loginDialogViewModel

    override fun getLayoutId(): Int = R.layout.dialog_login_fragment

    override fun getViewModel(): LoginDialogViewModel = loginDialogViewModel


    companion object {

        @JvmStatic
        fun getInstance(): LoginDialogFragment {
            val loginDialogFragment = LoginDialogFragment()
            val args = Bundle()
            loginDialogFragment.arguments = args

            return loginDialogFragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fb_login_button.fragment = this
        loginDialogViewModel.navigator = this
        registerFacebookLogin()
    }

    private fun registerFacebookLogin() {
        fb_login_button.setPermissions(EMAIL)
        //   fb_login_button.setReadPermissions(Arrays.asList(EMAIL));
        google_login_button.setOnClickListener { registerGoogleLogin() }
        // Callback registration
        fb_login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Util.showLog(TAG, "$loginResult")
                // App code
                val request = GraphRequest.newMeRequest(
                        loginResult.accessToken
                ) { fObject, response ->
                    try {
                        val sentIntent = Intent()
                        sentIntent.putExtra("fb_response", fObject.toString())
                        loginDialogViewModel.handleFbSignIn(context!!, fObject.toString())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                val parameters = Bundle()
                parameters.putString("fields", "id,name,email,age_range,birthday,education,gender,political,picture.type(square).width(200).height(200)")
                request.parameters = parameters
                request.executeAsync()
            }

            override fun onCancel() {
                // App code
                Util.showLog(TAG, "onCancel")
            }

            override fun onError(exception: FacebookException) {
                // App code
                Util.showLog(TAG, "onError")
            }
        })
        contact_login_button.setOnClickListener {
            onSignUpClick()
        }
    }

    private fun registerGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build()

        val mGoogleApiClient2 = GoogleApiClient.Builder(requireContext())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso) // gso
                .build()
        /*  EventTrackCommonApi.updateEventOnServer(context, ApiFieldConstant.USER_EVENT_LOGIN_GOOGLE,
                  ApiFieldConstant.USER_EVENT_ACTION_TYPE_1, "", "") { callback -> }*/
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient2)
        startActivityForResult(signInIntent, RequestUtil.GOOGLE_REQUEST)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RequestUtil.GOOGLE_REQUEST) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result != null) {
                loginDialogViewModel.handleGoogleResult(requireContext(), result)
            }
        }
    }

    override fun onSuccessLogin() {
        val targetFragment = targetFragment // fragment1 in our case
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, null)
        //listener.setLoginResult(true)
        dismiss()
    }

    private fun onSignUpClick() {
        val stringMobileNumberView = mobile_number_view.text.toString().trim { it <= ' ' }
        when {
            stringMobileNumberView.length > 9 -> {
                viewModel.handleSignUp(context!!, stringMobileNumberView)
            }
            stringMobileNumberView.isEmpty() -> Toast.makeText(context, getString(R.string.this_field_can_not_be_emty), Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(context, getString(R.string.number_should_digits), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSignUpResponse(number: String) {
    }
}