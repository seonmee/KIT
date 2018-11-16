package com.example.kit;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class NewsDetail extends AppCompatActivity {

    private WebView mWebView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);


        mWebView=(WebView)findViewById(R.id.webView);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        String content = bundle.getString("content");

        mWebView.setWebViewClient(new WebViewClient());
        WebSettings wSetting = mWebView.getSettings();
        wSetting.setJavaScriptEnabled(true);
        wSetting.setBuiltInZoomControls(true);
        mWebView.loadUrl(content);



    }
}