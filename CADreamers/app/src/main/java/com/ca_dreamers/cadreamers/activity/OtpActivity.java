package com.ca_dreamers.cadreamers.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.login_reg.otp_match.ModelOtp;
import com.ca_dreamers.cadreamers.models.login_reg.registration.ModelRegistration;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {

    String strUserName;
    String strUserMobile;
    String strUserEmail;
    String strUserPassword;
    String strUserOtp;
    String strUserOtpMatch;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvOtpMobile)
    protected AppCompatTextView tvOtpMobile;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etOtpNumber)
    protected AppCompatEditText etOtpNumber;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvOtpResend)
    protected TextView tvOtpResend;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnOtpSubmit)
    protected AppCompatButton btnOtpSubmit;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);
        strUserName = getIntent().getStringExtra(Constant.USER_NAME);
        strUserMobile = getIntent().getStringExtra(Constant.USER_MOBILE);
        strUserEmail = getIntent().getStringExtra(Constant.USER_EMAIL);
        strUserPassword = getIntent().getStringExtra(Constant.USER_PASS);
        strUserOtp = getIntent().getStringExtra(Constant.USER_OTP);
        tvOtpMobile.setText("Otp sent to your registered mobile number "+strUserMobile);
        tvOtpResend.setEnabled(false);
        init();

    }
    private void callRegSuccessApi(){

        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("name", strUserName);
            paramObject.put("mobile", strUserMobile);
            paramObject.put("email", strUserEmail);
            paramObject.put("password", strUserPassword);

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Log.d(Constant.TAG, "Request: "+gsonObject);
            Call<ModelOtp> userCall = api.getRegisterSuccess(gsonObject);
            userCall.enqueue(new Callback<ModelOtp>() {
                @Override
                public void onResponse(@NonNull Call<ModelOtp> call, @NonNull Response<ModelOtp> response) {
                    ModelOtp modelOtp = response.body();
                    assert modelOtp != null;
                    Toast.makeText(OtpActivity.this, modelOtp.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OtpActivity.this, LoginActivity.class));

                }
                @Override
                public void onFailure(@NonNull Call<ModelOtp> call, @NonNull Throwable t) {
                    boolean callExecuted = call.isExecuted();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnOtpSubmit)
    public void btnOtpSubmitClicked(){
        if (Objects.requireNonNull(etOtpNumber.getText()).toString().trim().equals("")){
            etOtpNumber.setError("Please enter the OTP.");
        }else {
            strUserOtpMatch = etOtpNumber.getText().toString().trim();
            if (strUserOtpMatch.equals(strUserOtp)){
                callRegSuccessApi();
            }else{
                Toast.makeText(this, "Please enter correct otp.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.tvOtpResend)
    public void tvOtpResendClicked(){
      callRegApi();
    }
    private void init() {
        new CountDownTimer(60000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {

                NumberFormat f = new DecimalFormat("00");

                long sec = (millisUntilFinished / 1000) % 60;

                tvOtpResend.setText("Request again after " + f.format(sec)+" seconds.");

            }

            @SuppressLint("SetTextI18n")
            public void onFinish() {
                tvOtpResend.setText("Resend Otp");
            }

        }.start();

    }
    private void callRegApi(){

        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("name", strUserName);
            paramObject.put("mobile", strUserMobile);
            paramObject.put("email", strUserEmail);
            paramObject.put("password", strUserPassword);

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelRegistration> userCall = api.getRegister(gsonObject);
            userCall.enqueue(new Callback<ModelRegistration>() {
                @Override
                public void onResponse(@NonNull Call<ModelRegistration> call, @NonNull Response<ModelRegistration> response) {
                    ModelRegistration modelRegistration = response.body();
                    assert modelRegistration != null;
                    strUserOtp  = String.valueOf(modelRegistration.getData().getOtp());

                    Toast.makeText(OtpActivity.this, modelRegistration.getMessage().getMessage(), Toast.LENGTH_LONG).show();
                    Log.d(Constant.TAG, "RESEND SUCCESS: " + modelRegistration.getData().getOtp());
                    tvOtpResend.setEnabled(false);
                    init();
                }

                @Override
                public void onFailure(@NonNull Call<ModelRegistration> call, @NonNull Throwable t) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}