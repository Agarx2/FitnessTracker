package com.example.nadirferlin.fitnesstracker;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class startActivity extends AppCompatActivity {

    WebView webView;
    Intent thisIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/sonic.html");
        thisIntent = new Intent(this, registrierung.class);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(thisIntent);
            }
        }, 2000);


    }
}
