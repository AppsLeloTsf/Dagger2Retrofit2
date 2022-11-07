package com.tsfapps.altrawing

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * JKS Password: Altrawing@2022
 */
class MainActivity : AppCompatActivity() {
     private lateinit var myWebView: WebView
     private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myWebView =  findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)
       Toast.makeText(this,"Welcome to Altrawing", Toast.LENGTH_SHORT).show()



        myWebView.webViewClient = WebViewClient()

        myWebView.settings.javaScriptEnabled = true;
        myWebView.loadUrl("http://altrawing.com/");
        myWebView.setInitialScale(1);
        myWebView.settings.builtInZoomControls = true;
        myWebView.settings.useWideViewPort = true;

    }

    inner class WebViewClient : android.webkit.WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return false
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (myWebView.canGoBack()) {
                        myWebView.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }
    override fun onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }

}


