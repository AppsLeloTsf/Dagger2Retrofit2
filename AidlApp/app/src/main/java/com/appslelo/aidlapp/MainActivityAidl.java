package com.appslelo.aidlapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivityAidl extends AppCompatActivity {

    private TextView textView;
    IAidlInterface iAidlInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_aidl);
        Intent intent = new Intent(this, MyAidlService.class);
        bindService(intent, bindService, Context.BIND_AUTO_CREATE);
       textView = findViewById(R.id.textView);
    }
    ServiceConnection bindService = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            iAidlInterface = IAidlInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @SuppressLint("SetTextI18n")
    public void Add(View view) {
        EditText editText = findViewById(R.id.editTextNumber);
        EditText editText2 = findViewById(R.id.editTextNumber2);
        int val1 = Integer.parseInt(editText.getText().toString());
        int val2 = Integer.parseInt(editText2.getText().toString());

        int result = 0;
        try {
            result = iAidlInterface.add(val1, val2);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        textView.setText(""+result);

    }
}