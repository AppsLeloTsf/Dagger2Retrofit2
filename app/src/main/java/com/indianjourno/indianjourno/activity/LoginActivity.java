package com.indainjourno.indianjourno.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.janatasuddi.janatasuddinews.R;
import com.janatasuddi.janatasuddinews.api.RetrofitClient;
import com.janatasuddi.janatasuddinews.model.login.LoginMessage;
import com.janatasuddi.janatasuddinews.storage.SharedPrefManager;
import com.janatasuddi.janatasuddinews.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private SharedPrefManager tSharedPrefManager;
    private Context tContext;
    @BindView(R.id.etEmailLogin)
    protected EditText etEmailLogin;
    @BindView(R.id.etPassLogin)
    protected EditText etPassLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        tContext = LoginActivity.this;
        tSharedPrefManager = new SharedPrefManager(tContext);




    }


    private void userLogin() {

        String email = etEmailLogin.getText().toString().trim();
        String password = etPassLogin.getText().toString().trim();


        if (email.isEmpty()) {
            etEmailLogin.setError("Email is required");
            etEmailLogin.requestFocus();
        }
       else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmailLogin.setError("Enter a valid email");
            etEmailLogin.requestFocus();
        }

       else if (password.isEmpty()) {
            etPassLogin.setError("Password required");
            etPassLogin.requestFocus();
        }

        else if (password.length() < 6) {
            etPassLogin.setError("Password should be 6 character long");
            etPassLogin.requestFocus();

        }else{
            callApiLogin(email, password);
        }



    }

    private void callApiLogin(String strEmail,  String strPassword){
        Call<LoginMessage> call = RetrofitClient
                .getInstance()
                .getApi()
                .userLogin(strEmail, strPassword);


       call.enqueue(new Callback<LoginMessage>() {
           @Override
           public void onResponse(Call<LoginMessage> call, Response<LoginMessage> response) {
               LoginMessage tModel = response.body();

               assert tModel != null;
               if (!tModel.getError()) {
                   String strId = tModel.getUsersDetails().getViwersId();
                   String strName = tModel.getUsersDetails().getViwersName();
                   String strMobile = tModel.getUsersDetails().getViwersMobile();
                   String strEmail = tModel.getUsersDetails().getViwersEmail();
                   tSharedPrefManager.setUserData(strId, strName, strMobile, strEmail);
                   Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                   Toast.makeText(LoginActivity.this, "Welcome " + strName, Toast.LENGTH_SHORT).show();
                   startActivity(intent);
                   finishAffinity();
                   finish();
               }
               else {
                   Toast.makeText(LoginActivity.this, tModel.getMessage(), Toast.LENGTH_SHORT).show();
               }
           }
           @Override
           public void onFailure(Call<LoginMessage> call, Throwable t) {
               Log.d(Constant.TAG, t.getMessage());
           }
       });
    }

@OnClick(R.id.btnLogin)
    public void btn_logClicked(View view){
    userLogin();
}
@OnClick(R.id.tvSignUp)
    public void tvSignUpClicked(View view){
    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
}

}