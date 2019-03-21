package com.ibrahim.soaqtask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TermsAndConditionsActivity extends AppCompatActivity {

    private WebView mWebview ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);



        mWebview  = new WebView(this);

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript


        mWebview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {

            }
        });

        mWebview .loadUrl("https://termsfeed.com/blog/sample-terms-and-conditions-template/");
        setContentView(mWebview );

    }
}
