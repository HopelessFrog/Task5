package com.example.task5;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;
    private class SimpleWebViewClient extends WebViewClient {
        private Activity activity = null;

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            if (url.contains(getString(R.string.Url))) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(intent);
            return true;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setDisplayZoomControls(false);

        webView.getSettings().setUseWideViewPort(true);

        webView.getSettings().setLoadWithOverviewMode(true);

        webView.setForceDarkAllowed(true);



        webView.setWebViewClient(new SimpleWebViewClient());

        webView.addJavascriptInterface(new Object() {
            @android.webkit.JavascriptInterface
            public void showToast(String message) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                });
            }


        }, "Android");

        webView.loadUrl(getString(R.string.Url));
    }
}
