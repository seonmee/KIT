package com.example.kit;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kit.Adapter.NewsAdapter;
import com.example.kit.Bean.newsBean;

import java.net.URI;

public class NewsDetail extends AppCompatActivity {

    private WebView mWebView;
    private RecyclerView.Adapter mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);


        mWebView=(WebView)findViewById(R.id.webView);

        final Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        final String content = bundle.getString("content");

        mWebView.setWebViewClient(new WebViewClient());
        WebSettings wSetting = mWebView.getSettings();
        wSetting.setJavaScriptEnabled(true);
        wSetting.setBuiltInZoomControls(true);
        mWebView.loadUrl(content);


        findViewById(R.id.clip).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {


                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(content));

                        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

                        ClipData clipData = ClipData.newIntent("link",intent);
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(getApplication(), "주소가 복사되었습니다.",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
}