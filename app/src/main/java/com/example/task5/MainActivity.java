package com.example.task5;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true); // Включаем поддержку JavaScript

        // Настройка WebViewClient для обработки ссылок внутри WebView
        webView.setWebViewClient(new WebViewClient());

        // Настройка интерфейса для вызова JavaScript функций
        webView.addJavascriptInterface(new Object() {
            @android.webkit.JavascriptInterface
            public void showToast(String message) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                });
            }

            @android.webkit.JavascriptInterface
            public void showDialog(String title, String message) {
                runOnUiThread(() -> {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle(title)
                            .setMessage(message)
                            .setPositiveButton("ОК", (dialog, which) -> dialog.dismiss())
                            .show();
                });
            }
        }, "Android");

        // Загрузите вашу HTML-страницу
        webView.loadUrl("http://10.0.2.2:4987/index.html");
    }
}
