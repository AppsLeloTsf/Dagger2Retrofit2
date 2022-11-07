package com.ca_dreamers.cadreamers.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;


import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class PdfActivity extends AppCompatActivity{

    private String strPdfUrl;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.idPDFView)
    protected PDFView idPDFView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_loading)
    protected TextView tv_loading;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ivDownloadPdf)
    protected AppCompatImageView ivDownloadPdf;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.progressBar)
    protected ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_pdf);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.VISIBLE);
        strPdfUrl = getIntent().getStringExtra(Constant.BOOKS_PDF_URL);
        Log.d("BOOKS_PDF_URL", strPdfUrl);

        new Thread(() -> {
            InputStream inputStream = null;
            try {
                URL url = new URL(strPdfUrl);
                HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream finalInputStream = inputStream;
            runOnUiThread(() -> {
                idPDFView.fromStream(finalInputStream).load();
                progressBar.setVisibility(View.GONE);

            });
        }).start();
    }
}