package com.example.nadirferlin.fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class registrierung extends AppCompatActivity {

    public static boolean registriert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrierung);
    }

    public void signUp(View v){
        Intent thisIntent = new Intent(this, Hauptbildschirm.class);
        startActivity(thisIntent);
    }
}
