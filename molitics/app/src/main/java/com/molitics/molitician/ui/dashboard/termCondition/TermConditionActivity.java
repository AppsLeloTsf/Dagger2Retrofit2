package com.molitics.molitician.ui.dashboard.termCondition;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.UiMediator.Loader;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.NetworkUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.molitics.molitician.util.Constant.IntentKey.TOOL_BAR;

/**
 * Created by rahul on 1/18/2017.
 */

public class TermConditionActivity extends BasicAcivity implements TermPresenter.TermPresenterView, ViewRefreshListener {


    @BindView(R.id.term_toolbar)
    Toolbar toolbar;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.main_progress_bar)
    ProgressBar main_progress_bar;

    private TermsHandler termsHandler;
    private String tool_text;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_terms_condition);
        ButterKnife.bind(this);

        Intent mIntent = getIntent();
        tool_text = mIntent.getStringExtra(TOOL_BAR);

        String url = mIntent.getStringExtra(Constant.IntentKey.URL);

        termsHandler = new TermsHandler(this);

        setActionBar();
        //webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new MyBrowser());

        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setJavaScriptEnabled(true);
        if (NetworkUtil.isNetworkConnected(this)) {
            main_progress_bar.setVisibility(View.VISIBLE);
            webView.loadUrl(url);
        }
    }

    public void setActionBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
        toolbar.setTitle(tool_text);

        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void onTermsResponse(APIResponse apiResponse) {
        Loader.dismissMyDialog(TermConditionActivity.this);
    }

    @Override
    public void onTermsApiException(ApiException apiException) {
        Loader.dismissMyDialog(this);
    }

    @Override
    public void onRefereshClick() {
        Loader.showMyDialog(TermConditionActivity.this);
        termsHandler.getTerms();
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }


    private class MyBrowser extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            main_progress_bar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }

        @TargetApi(android.os.Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(final WebView view, final WebResourceRequest request, final WebResourceError error) {
            super.onReceivedError(view, request, error);

        }
    }
}
