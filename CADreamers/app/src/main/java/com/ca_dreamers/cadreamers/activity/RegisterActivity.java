package com.ca_dreamers.cadreamers.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.login_reg.registration.ModelRegistration;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etRegName)
    protected TextInputLayout etRegName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etPhoneReg)
    protected TextInputLayout etPhoneReg;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etRegEmail)
    protected TextInputLayout etRegEmail;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etPasswordReg)
    protected TextInputLayout etPasswordReg;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cbPrivacyReg)
    protected AppCompatCheckBox cbPrivacyReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    private void callRegApi(){
        String strUserName = Objects.requireNonNull(etRegName.getEditText()).getText().toString().trim();
        String strUserMobile = Objects.requireNonNull(etPhoneReg.getEditText()).getText().toString().trim();
        String strUserEmail= Objects.requireNonNull(etRegEmail.getEditText()).getText().toString().trim();
        String strPassword = Objects.requireNonNull(etPasswordReg.getEditText()).getText().toString().trim();
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("name", strUserName);
            paramObject.put("mobile", strUserMobile);
            paramObject.put("email", strUserEmail);
            paramObject.put("password", strPassword);

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelRegistration> userCall = api.getRegister(gsonObject);
            userCall.enqueue(new Callback<ModelRegistration>() {
                @Override
                public void onResponse(@NonNull Call<ModelRegistration> call, @NonNull Response<ModelRegistration> response) {
                   ModelRegistration modelRegistration = response.body();
                    assert modelRegistration != null;
                    String strOtp = String.valueOf(modelRegistration.getData().getOtp());
                    Toast.makeText(RegisterActivity.this, modelRegistration.getMessage().getMessage(), Toast.LENGTH_LONG).show();
                    Log.d(Constant.TAG, "OTP: "+strOtp);
                    Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
                    intent.putExtra(Constant.USER_NAME, strUserName);
                    intent.putExtra(Constant.USER_MOBILE, strUserMobile);
                    intent.putExtra(Constant.USER_EMAIL, strUserEmail);
                    intent.putExtra(Constant.USER_PASS, strPassword);
                    intent.putExtra(Constant.USER_OTP, strOtp);
                    startActivity(intent);
                }

                @Override
                public void onFailure(@NonNull Call<ModelRegistration> call, @NonNull Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Email or Mobile no. already exist!", Toast.LENGTH_LONG).show();
                    Log.d("TSF_REG", "REG FAILURE: "+t.getMessage());
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.tvGoLoginReg)
    void btnSignInClicked() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finishAffinity();
    }
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnSignUpReg)
    void btnSignUpRegClicked() {
        if (Objects.requireNonNull(etRegName.getEditText()).getText().toString().trim().equals("")){
            etRegName.setError("Name is required.");
        }else if(Objects.requireNonNull(etPhoneReg.getEditText()).getText().toString().trim().equals("")){
            etPhoneReg.setError("Phone number is required");
        }else if(Objects.requireNonNull(etRegEmail.getEditText()).getText().toString().trim().equals("")){
            etRegEmail.setError("Email is required.");
        }else if(Objects.requireNonNull(etPasswordReg.getEditText()).getText().toString().trim().equals("")){
            etPasswordReg.setError("Password is required");
        }
        else if (!cbPrivacyReg.isChecked()) {
            cbPrivacyReg.setError("Can't left unchecked.");
            Toast.makeText(this, "To proceed, must have to check privacy and policy.", Toast.LENGTH_SHORT).show();
        }else {
            callRegApi();
             }
    }


}