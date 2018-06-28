package com.example.nadirferlin.fitnesstracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

/*
 * Beginn der App welche 2 Sekunden lang gif zeigt
 */
public class IntroActivity extends AppCompatActivity {

    WebView webView;
    Intent thisIntent;
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/sonic.html");

        myDb = new DatabaseHelper(this);
        Cursor cursor = myDb.getAllData();

        if(cursor.getCount() != 0){
            thisIntent = new Intent(this, MainPageActivity.class);

        }
        else {
            thisIntent = new Intent(this, RegisterActivity.class);
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(thisIntent);
            }
        }, 2000);
    }
}
