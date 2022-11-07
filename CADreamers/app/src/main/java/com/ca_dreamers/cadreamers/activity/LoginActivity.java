package com.ca_dreamers.cadreamers.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.logged_out.ModelLogout;
import com.ca_dreamers.cadreamers.models.login_reg.login.ModelLogin;
import com.ca_dreamers.cadreamers.models.login_reg.password.ModelForgotPassword;
import com.ca_dreamers.cadreamers.models.login_reg.password.update_pass.ModelUpdatePassword;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private String strLoginStatus;
    private String strOtp;
    private Context tContext;
    private AlertDialog dialogForgotPass;
    private AlertDialog dialog;
    private SharedPrefManager sharedPrefManager;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.pbLoginActivity)
    protected ProgressBar pbLoginActivity;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etUserNameLogin)
    protected TextInputLayout etUserNameLogin;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etPasswordLogin)
    protected TextInputLayout etPasswordLogin;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnSignInLogin)
    protected AppCompatButton btnSignInLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        tContext = getApplicationContext();
        sharedPrefManager = new SharedPrefManager(tContext);
    }

    private void callLoginApi(){
        String strUserName = Objects.requireNonNull(etUserNameLogin.getEditText()).getText().toString().trim();
        String strPassword = Objects.requireNonNull(etPasswordLogin.getEditText()).getText().toString().trim();
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("email", strUserName);
            paramObject.put("password", strPassword);

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelLogin> userCall = api.getLogin(gsonObject);

            userCall.enqueue(new Callback<ModelLogin>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NotNull Call<ModelLogin> call, @NotNull Response<ModelLogin> response) {
                    ModelLogin modelLogin = response.body();
                    Log.d("TSF_RESPONSE", response.body().getData().getUserToken()+"\t"+response.body().getData().getUsername()+"\t"+response.body().getData().getLoggedIn());
                    pbLoginActivity.setVisibility(View.GONE);
                    assert modelLogin != null;
                    if(modelLogin.getStatus().getStatuscode().equals("200")) {
                        strLoginStatus = modelLogin.getData().getLoggedIn();
                        if(!strLoginStatus.equals("1")) {
                            String strUserId = modelLogin.getData().getId();
                            String strUserName = modelLogin.getData().getUsername();
                            String strUserMobile = modelLogin.getData().getPhone();
                            String strUserEmail = modelLogin.getData().getEmail();
                            String strUserPic = modelLogin.getData().getPicture();
                            String strUserToken = modelLogin.getData().getUserToken();
                            sharedPrefManager.setUserData(strUserId, strUserName, strUserMobile, strUserEmail, strUserPic, strUserToken);
                            callApiLoggedIn(strUserId, modelLogin.getData().getPicture());
                            Toast.makeText(tContext, modelLogin.getMessage().getMessage(), Toast.LENGTH_SHORT).show();

                        }else {
                            AlertDialog dialogUpdateApp;
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            final View customLayout = getLayoutInflater().inflate(R.layout.custom_login_status, null);
                            builder.setView(customLayout);
                            AppCompatTextView tvUpdateNote = customLayout.findViewById(R.id.tvLoginStatus);
                            tvUpdateNote.setText("User is already logged in a device, ask to logout first !!!");
                            AppCompatButton btnLoginStatus = customLayout.findViewById(R.id.btnLoginCancel);
                            btnLoginStatus.setOnClickListener(v -> finishAffinity());

                            AppCompatButton btnLoginLogout = customLayout.findViewById(R.id.btnLoginOkay);
                            btnLoginLogout.setOnClickListener(v -> callLogoutApi(modelLogin.getData().getId()));

                            dialogUpdateApp = builder.create();
                            dialogUpdateApp.getWindow().setBackgroundDrawableResource(R.color.white);
                            dialogUpdateApp.show();
                            dialogUpdateApp.setCancelable(false);
                        }
                    }
                    else {
                        Toast.makeText(tContext, modelLogin.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(@NotNull Call<ModelLogin> call, @NotNull Throwable t) {
                    Toast.makeText(tContext, "Credentials is invalid.", Toast.LENGTH_LONG).show();
                    pbLoginActivity.setVisibility(View.GONE);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnSignInLogin)
   void btnSignInLoginClicked() {
        if (Objects.requireNonNull(etUserNameLogin.getEditText()).getText().toString().trim().equals("")){
            etUserNameLogin.setError("Name is required.");
        }else if(Objects.requireNonNull(etPasswordLogin.getEditText()).getText().toString().trim().equals("")){
            etPasswordLogin.setError("Phone number is required");
        }else  {
            pbLoginActivity.setVisibility(View.VISIBLE);
            callLoginApi();
        }
    }
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.tvGoRegLogin)
   void btnSignUpLoginClicked() {
        startActivity(new Intent(tContext, RegisterActivity.class));
    }
  @SuppressLint("NonConstantResourceId")
  @OnClick(R.id.tvForgotPassword)
   void tvForgotPasswordClicked() {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      final View customLayout = getLayoutInflater().inflate(R.layout.custom_pass_forgot, null);
      builder.setView(customLayout);
                  TextInputLayout etForgotPassEmail = customLayout.findViewById(R.id.etForgotPassword);
                  AppCompatButton btnForgotPass = customLayout.findViewById(R.id.btnForgotPass);
                  btnForgotPass.setOnClickListener(v -> callApiForgotPass(Objects.requireNonNull(etForgotPassEmail.getEditText()).getText().toString()));

      dialogForgotPass = builder.create();
      dialogForgotPass.getWindow().setBackgroundDrawableResource(R.color.white);
      dialogForgotPass.show();
  }
    @SuppressLint("HardwareIds")
    public String getUserDeviceId() {
        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(
                    getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(
                        getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
        }

        return deviceId;
    }
  private void callApiLoggedIn(String strUserId, String strProfilePic)
  {

     String strDeviceId = getUserDeviceId();

        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", strUserId);
            paramObject.put("token_device", strDeviceId);

            Log.d("TSF_APPS", "User ID: "+strUserId);
            Log.d("TSF_APPS", "Device ID: "+strDeviceId);
            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<Void> userCall = api.getLoggedIn(gsonObject);

            userCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                    btnSignInLogin.setEnabled(false);
                    Intent intent = new Intent(tContext, MainActivity.class);
                    intent.putExtra(Constant.USER_PRO_PIC, strProfilePic);
                    startActivity(intent);
                    finish();
                       Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                    Log.d("TSF_APPS", "LOGGED IN FAILURE: "+t.getMessage());

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
  private void callApiForgotPass(String strUserEmail)
    {
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("email", strUserEmail);

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelForgotPassword> userCall = api.forgotPassword(gsonObject);

            userCall.enqueue(new Callback<ModelForgotPassword>() {
                @Override
                public void onResponse(@NotNull Call<ModelForgotPassword> call, @NotNull Response<ModelForgotPassword> response) {
                    ModelForgotPassword modelForgotPassword = response.body();
                    assert modelForgotPassword != null;
                    strOtp = String.valueOf(modelForgotPassword.getData().getOtp());
                       updatePassAlert(strUserEmail, strOtp);
                       dialogForgotPass.dismiss();
                       Toast.makeText(LoginActivity.this, modelForgotPassword.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(@NotNull Call<ModelForgotPassword> call, @NotNull Throwable t) {
                    Log.d("TSF_FORGOT_PASS", "RESPONSE FAILURE: "+t.getMessage());
                    Toast.makeText(LoginActivity.this, "Email does not exist or Something went wrong, Please try again later", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updatePassAlert(String strEmail, String strOtp)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(false);
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_pass_update, null);
        builder.setView(customLayout);

        TextInputLayout etOTPPass = customLayout.findViewById(R.id.etOTPPass);
        TextInputLayout etPasswordUpdate = customLayout.findViewById(R.id.etPasswordUpdate);
        TextInputLayout etPasswordUpdateAgain = customLayout.findViewById(R.id.etPasswordUpdateAgain);
        AppCompatButton btnForgotPass = customLayout.findViewById(R.id.btnPassUpdate);
        AppCompatButton btnPassUpdateCancel = customLayout.findViewById(R.id.btnPassUpdateCancel);
        btnForgotPass.setOnClickListener(v -> {
            if (Objects.requireNonNull(etOTPPass.getEditText()).getText().toString().equals(strOtp)){
                String strEtPassUpdate = Objects.requireNonNull(etPasswordUpdate.getEditText()).getText().toString();
                String strEtPassUpdateAgain = Objects.requireNonNull(etPasswordUpdateAgain.getEditText()).getText().toString();
                if (strEtPassUpdate.equals(strEtPassUpdateAgain)){
                    callApiUpdatePass(strEmail, strEtPassUpdate);
                }
            }
            else {
                Toast.makeText(tContext, "Otp did not match", Toast.LENGTH_SHORT).show();
            }
        });

        btnPassUpdateCancel.setOnClickListener(v -> dialog.dismiss());
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.color.white);
        dialog.show();
    }

    private void callApiUpdatePass(String strUserEmail, String strPassword)
    {
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("email", strUserEmail);
            paramObject.put("password", strPassword);
            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelUpdatePassword> userCall = api.updatePassword(gsonObject);
            userCall.enqueue(new Callback<ModelUpdatePassword>() {
                @Override
                public void onResponse(@NotNull Call<ModelUpdatePassword> call, @NotNull Response<ModelUpdatePassword> response) {
                    ModelUpdatePassword modelUpdatePassword = response.body();
                    dialog.dismiss();
                    assert modelUpdatePassword != null;
                    Toast.makeText(tContext, modelUpdatePassword.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(@NotNull Call<ModelUpdatePassword> call, @NotNull Throwable t) {
                    Log.d("TSF_UPDATE_PASS", "RESPONSE FAILURE: "+t.getMessage());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void callLogoutApi(String strUserId){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", strUserId);
            Log.d("TSF_APPS", "User Id: "+strUserId);
            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelLogout> call = api.getLogout(gsonObject);
            call.enqueue(new Callback<ModelLogout>() {
                @Override
                public void onResponse(@NonNull Call<ModelLogout> call, @NonNull Response<ModelLogout> response) {
                    callLoginApi();
                }

                @Override
                public void onFailure(@NonNull Call<ModelLogout> call, @NonNull Throwable t) {
                    Log.d("TSF_APPS", "Logout Error: "+t);

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}