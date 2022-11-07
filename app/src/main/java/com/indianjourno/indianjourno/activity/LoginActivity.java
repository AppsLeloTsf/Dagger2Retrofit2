package com.indianjourno.indianjourno.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.model.ij_user.ModelUserLogin;
import com.indianjourno.indianjourno.model.login.LoginMessage;
import com.indianjourno.indianjourno.storage.SharedPrefManager;
import com.indianjourno.indianjourno.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import indianjourno.indianjourno.R;
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
        Call<ModelUserLogin> call = RetrofitClient
                .getInstance()
                .getApi()
                .userLogin(strEmail, strPassword);


       call.enqueue(new Callback<ModelUserLogin>() {
           @Override
           public void onResponse(@NonNull Call<ModelUserLogin> call, @NonNull Response<ModelUserLogin> response) {
               ModelUserLogin tModel = response.body();

               assert tModel != null;
               if (!tModel.getError()) {
                   String strId = tModel.getUsers().getViwersId();
                   String strName = tModel.getUsers().getViwersName();
                   String strMobile = tModel.getUsers().getViwersMobile();
                   String strEmail = tModel.getUsers().getViwersEmail();
                   tSharedPrefManager.setUserData(strId, strName, strMobile, strEmail);
                   Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                   Toast.makeText(LoginActivity.this, "Welcome " + strName, Toast.LENGTH_SHORT).show();
                   startActivity(intent);
                   finishAffinity();
                   finish();
               }
               else {
                   Toast.makeText(LoginActivity.this, "Credentials does not matching", Toast.LENGTH_SHORT).show();
               }
           }
           @Override
           public void onFailure(@NonNull Call<ModelUserLogin> call, @NonNull Throwable t) {
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