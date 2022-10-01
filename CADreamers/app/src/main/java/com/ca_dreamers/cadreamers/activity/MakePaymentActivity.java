package com.ca_dreamers.cadreamers.activity.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.utils.Constant;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MakePaymentActivity extends AppCompatActivity {

    private String strPaymentUrl;

    @BindView(R.id.wbMakePayment)
    protected WebView wbMakePayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);
        ButterKnife.bind(this);
        wbMakePayment.setWebViewClient(new MyWebViewClient());
        strPaymentUrl = getIntent().getStringExtra(Constant.PAYMENT_URL);
        wbMakePayment.getSettings().setJavaScriptEnabled(true);
        wbMakePayment.loadUrl(strPaymentUrl);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url); // load the url
            return true;
        }
    }
}