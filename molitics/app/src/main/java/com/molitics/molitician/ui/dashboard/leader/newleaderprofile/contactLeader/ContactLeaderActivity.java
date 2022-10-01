package com.molitics.molitician.ui.dashboard.leader.newleaderprofile.contactLeader;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.UiMediator.Loader;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.leader.model.ProblemPostModel;
import com.molitics.molitician.ui.dashboard.userProfile.editContact.EditContactHandler;
import com.molitics.molitician.ui.dashboard.userProfile.editContact.EditNumberPresenter;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rahul on 6/30/2017.
 */

public class ContactLeaderActivity extends BasicAcivity implements ContactLeaderPresenter.ContactView, EditNumberPresenter.EditContactView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.leader_image)
    CircleImageView leader_image;
    @BindView(R.id.leader_name_view)
    TextView leader_name_view;
    @BindView(R.id.leader_position_view)
    TextView leader_position_view;
    @BindView(R.id.name_view)
    EditText name_view;
    @BindView(R.id.contact_view)
    EditText contact_view;
    @BindView(R.id.topic_view)
    EditText topic_view;
    @BindView(R.id.description_view)
    EditText description_view;
    @BindView(R.id.submit_issue)
    TextView submit_issue;
    @BindView(R.id.base_container)
    FrameLayout base_container;

    private ContactLeaderHandler leaderHandler;
    private EditContactHandler editContactHandler;

    private int leader_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_leader);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        showHeader(true, getResources().getString(R.string.contact_leader_header));

        editContactHandler = new EditContactHandler(this);

        toolbar.setNavigationOnClickListener(v -> goBack());
        leaderHandler = new ContactLeaderHandler(this);
        // handle intent
        Intent mIntent = getIntent();
        leader_id = mIntent.getIntExtra(Constant.IntentKey.LEADER_PROFILE_ID, 0);
        String leader_name = mIntent.getStringExtra(Constant.IntentKey.LEADER_NAME);
        String l_position = mIntent.getStringExtra(Constant.IntentKey.LEADER_POSITION);
        String l_image = mIntent.getStringExtra(Constant.IntentKey.LEADER_IMAGE);

        if (!l_image.isEmpty())
            Picasso.with(this).load(l_image).placeholder(getResources().getDrawable(R.drawable.sample_image)).into(leader_image);
        else
            leader_image.setImageResource(R.drawable.sample_image);
        leader_name_view.setText(leader_name);
        leader_position_view.setText(l_position);

        String user_name = PrefUtil.getString(Constant.PreferenceKey.USER_NAME);
        String contact_number = PrefUtil.getString(Constant.PreferenceKey.USER_CONTACT);
        if (!TextUtils.isEmpty(user_name)) {
            name_view.setText(user_name);
            name_view.setSelection(name_view.getText().length());
            name_view.setEnabled(false);
        }
        if (!TextUtils.isEmpty(contact_number)) {
            contact_view.setText(contact_number);
            contact_view.setSelection(contact_view.getText().length());
            contact_view.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goBack();
    }

    @OnClick(R.id.submit_issue)
    void onSubmitClick() {
        ProblemPostModel problemPostModel = new ProblemPostModel();
        String user_name = name_view.getText().toString();
        String user_contact = contact_view.getText().toString().trim();
        String user_subject = topic_view.getText().toString();
        String user_problem = description_view.getText().toString();

        if (TextUtils.isEmpty(PrefUtil.getString(Constant.PreferenceKey.USER_CONTACT)) && !TextUtils.isEmpty(user_contact)) {
            Loader.showMyDialog(this);
            editContactHandler.submitEditContact(user_contact);
        } else if (problemPostValidate()) {
            submit_issue.setClickable(false);
            submit_issue.setFocusable(false);
            problemPostModel.setName(user_name);
            problemPostModel.setContact(user_contact);
            problemPostModel.setSubject(user_subject);
            problemPostModel.setDescription(user_problem);
            Loader.showMyDialog(this);
            leaderHandler.submitProblem(leader_id, problemPostModel);
        }
    }

    private boolean problemPostValidate() {
        boolean isPass = true;
        String user_name = name_view.getText().toString();
        String user_contact = contact_view.getText().toString().trim();
        String user_subject = topic_view.getText().toString();
        String user_description = description_view.getText().toString();

        if (user_name.isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_first_name), Toast.LENGTH_SHORT).show();
            isPass = false;
        } else if (user_contact.isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_contact), Toast.LENGTH_SHORT).show();
            isPass = false;
        } else if (user_subject.isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_subject), Toast.LENGTH_SHORT).show();
            isPass = false;
        } else if (user_description.isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_description), Toast.LENGTH_SHORT).show();
            isPass = false;
        }
        return isPass;
    }

    @Override
    public void onContactResponse(APIResponse apiResponse) {
        Loader.dismissMyDialog(this);

    }

    @Override
    public void onContactApiException(ApiException apiException) {
        Loader.dismissMyDialog(this);
        if (apiException.getCode() == 2004 || apiException.getCode() == 2005) {
            base_container.setVisibility(View.VISIBLE);
            showHeader(true, getString(R.string.enter_otp));
            Bundle otp_bundle = new Bundle();
            otp_bundle.putString("number", contact_view.getText().toString().trim());
            otp_bundle.putString("url", "contactchangeotp");
            //replaceFragment(new EnterOtp(), otp_bundle, true, false);
        } else {
            Util.validateError(this, apiException, null, null, null);
        }
    }

    @Override
    public void onOtpResponse(APIResponse apiResponse) {
    }

    @Override
    public void onOtpApiException(ApiException apiException) {
        Loader.dismissMyDialog(this);
    }

    @Override
    public void onContactServerError(ServerException serverException) {
    }

    @Override
    public void onContactResponseException(ApiException apiException) {
        Loader.dismissMyDialog(this);
        if (apiException.getCode() == 2005) {
            Toast.makeText(this, getString(R.string.sucess), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, getString(R.string.fail_please_try_again), Toast.LENGTH_SHORT).show();
        }
    }
}
