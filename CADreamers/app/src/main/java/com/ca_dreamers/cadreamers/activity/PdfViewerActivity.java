package com.ca_dreamers.cadreamers.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.github.barteksc.pdfviewer.PDFView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PdfViewerActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.pdfView)
    protected PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        ButterKnife.bind(this);
        Toast.makeText(this, "Opening book...", Toast.LENGTH_LONG).show();
        String strUrl = getIntent().getStringExtra(Constant.BOOKS_PDF_URL);
        String u = "file:///" + strUrl;
        pdfView.fromUri(Uri.parse(u)).load();
    }
}