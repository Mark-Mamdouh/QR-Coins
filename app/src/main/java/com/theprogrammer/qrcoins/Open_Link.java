package com.theprogrammer.qrcoins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Open_Link extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open__link);

        Intent intent = getIntent();
        String URL = intent.getStringExtra("URL");

        WebView webView = (WebView) findViewById(R.id.web);

        // enable javascript because it is disabled as default
        webView.getSettings().setJavaScriptEnabled(true);

        // make the webview display your url instead of opening your browser
        webView.setWebViewClient(new WebViewClient());

        // load your url
        if(Patterns.WEB_URL.matcher(URL).matches()) {
            webView.loadUrl(URL);
        } else {
            webView.loadUrl("www.google.com/search?q=" + URL);
        }
    }
}