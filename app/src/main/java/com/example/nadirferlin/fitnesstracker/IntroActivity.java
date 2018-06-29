package com.example.nadirferlin.fitnesstracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Beginn der App welche 2 Sekunden lang ein Gif zeigt
 * @author Manuel Dutli
 */
public class IntroActivity extends AppCompatActivity {

    WebView webView;
    Intent thisIntent;
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState   ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //Verweist auf das gespeicherte GIF im Android Asset
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/sonic.html");

        //Prüft ob schon Informationen in der DB stehen um die nächste Activity zu wählen
        myDb = new DatabaseHelper(this);
        Cursor cursor = myDb.getAllData();

        //Falls der Inhalt der DB nicht 0 ist öffnet es die Hauptseite, sonst die Registrierung
        if(cursor.getCount() != 0){
            thisIntent = new Intent(this, MainPageActivity.class);

        } else {
            thisIntent = new Intent(this, RegisterActivity.class);
        }

        //Verzögert den nächsten Schritt um 2 Sekunden
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(thisIntent);
            }
        }, 2000);
    }
}
