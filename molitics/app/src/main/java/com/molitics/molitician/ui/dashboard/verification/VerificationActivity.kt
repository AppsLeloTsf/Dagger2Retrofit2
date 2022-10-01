package com.molitics.molitician.ui.dashboard.verification

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.molitics.molitician.BaseActivity
import com.molitics.molitician.R
import com.molitics.molitician.BR
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.databinding.ActivityVerificationBinding
import com.molitics.molitician.ui.dashboard.verification.viewModel.VerificationActivityViewModel
import com.molitics.molitician.util.Constant.GALLERY_IMAGE_REQUEST_CODE
import com.molitics.molitician.util.MoliticsAppPermission.checkCameraAndWritePermission
import com.molitics.molitician.util.MoliticsAppPermission.requestCameraPermission
import com.molitics.molitician.util.Util
import kotlinx.android.synthetic.main.activity_verification.*
import java.io.File
import javax.inject.Inject

class VerificationActivity :
    BaseActivity<ActivityVerificationBinding, VerificationActivityViewModel>(),
    VerificationActivityInterface {

    @Inject
    lateinit var verificationFactory: MyViewModelFactory<VerificationActivityViewModel>

    private val verificationViewModel: VerificationActivityViewModel by lazy {
        ViewModelProvider(this, verificationFactory)[VerificationActivityViewModel::class.java]
    }

    override fun getBindingVariable(): Int = BR.verificationViewModel

    override fun getLayoutId(): Int = R.layout.activity_verification

    override fun getViewModel(): VerificationActivityViewModel = verificationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verificationViewModel.navigator = this
        userStatusRadioListener()
        citizenRadioListener()
        handleClick()
    }

    private fun userStatusRadioListener() {
        userStatusRB.setOnCheckedChangeListener { group, checkedId ->
            val radioButtonID = group.checkedRadioButtonId
            val radioButton = group.findViewById<View>(radioButtonID)
            val idx = group.indexOfChild(radioButton)
            verificationViewModel.userStatus = idx.plus(1)
        }
    }

    private fun handleClick() {
        selectDocumentNameView.setOnClickListener {
            if (!checkCameraAndWritePermission()) {
                requestCameraPermission(this)
            } else {
                uploadDoc()
            }
        }
    }

    private fun citizenRadioListener() {
        citizenRB.setOnCheckedChangeListener { group, checkedId ->
            val radioButtonID = group.checkedRadioButtonId
            val radioButton = group.findViewById<View>(radioButtonID)
            val idx = group.indexOfChild(radioButton)
            verificationViewModel.citizen = idx.plus(1)
        }
    }

    fun uploadDoc() {
        val options: Options = Options.init()
            .setRequestCode(GALLERY_IMAGE_REQUEST_CODE) //Request code for activity results
            .setCount(1) //Number of images to restict selection count
            .setFrontfacing(false) //Front Facing camera on start
            //.setPreSelectedUrls(returnValue) //Pre selected Image Urls
            .setSpanCount(4) //Span count for gallery min 1 & max 5
            .setExcludeVideos(true) //Option to exclude videos
            .setVideoDurationLimitinSeconds(30) //Duration for video recording
            .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT) //Orientaion
            .setPath("/images") //Custom Path For media Storage

        Pix.start(this, options)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                GALLERY_IMAGE_REQUEST_CODE -> {
                    val selectedImages = data!!.getStringArrayListExtra(Pix.IMAGE_RESULTS)
                    val imagePath = selectedImages[0]
                    verificationViewModel.docImage = imagePath
                    documentNameView.text = File(imagePath).name
                }
            }
        }
    }

    override fun onVerificationSubmit() {
        Util.showToast(this, getString(R.string.verification_form_submit_message))
        finish()
    }
}