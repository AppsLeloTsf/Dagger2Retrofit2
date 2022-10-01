package com.indainjourno.indianjourno.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.janatasuddi.janatasuddinews.R;
import com.janatasuddi.janatasuddinews.api.RetrofitClient;
import com.janatasuddi.janatasuddinews.model.ModelRegister;
import com.janatasuddi.janatasuddinews.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.etMobileReg)
    protected EditText etMobileReg;
    @BindView(R.id.etEmailReg)
    protected EditText etEmailReg;
    @BindView(R.id.etNameReg)
    protected EditText etNameReg;
    @BindView(R.id.etPassReg)
    protected EditText etPassReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void userSignUp() {
        String mobile = etMobileReg.getText().toString().trim();
        String email = etEmailReg.getText().toString().trim();
        String name = etNameReg.getText().toString().trim();
        String password = etPassReg.getText().toString().trim();


        if (email.isEmpty()) {
            etEmailReg.setError("Email is required");
            etEmailReg.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmailReg.setError("Enter a valid email");
            etEmailReg.requestFocus();
        }
        else if (password.isEmpty()) {
            etPassReg.setError("Password required");
            etPassReg.requestFocus();
        }
        else if (password.length() < 6) {
            etPassReg.setError("Password should be 6 character long");
            etPassReg.requestFocus();
        }
        else callApiRegister(mobile, email, name, password);
    }
    private void callApiRegister(String strMobile, String strEmail, String strName, String strPass){
        Call<ModelRegister> call = RetrofitClient
                .getInstance()
                .getApi()
                .userRegister(strMobile, strEmail,strName, strPass);
        call.enqueue(new Callback<ModelRegister>() {
            @Override
            public void onResponse(Call<ModelRegister> call, Response<ModelRegister> response) {
                ModelRegister tModel = response.body();
                assert tModel != null;
                if (!tModel.getError()) {
                    Toast.makeText(getApplicationContext(), tModel.getMessage(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finishAffinity();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), tModel.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ModelRegister> call, Throwable t) {
                Log.d(Constant.TAG, t.getMessage());
            }
        });
    }
    @OnClick(R.id.btnReg)
    public void btnRegClicked(View view){
        userSignUp();
    }
    @OnClick(R.id.tvSignIn)
    public void tvSignInClicked(View view){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finishAffinity();
        finish();
    }
}
