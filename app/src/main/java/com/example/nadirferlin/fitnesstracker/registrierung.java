package com.example.nadirferlin.fitnesstracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class registrierung extends AppCompatActivity {

    public static boolean registriert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrierung);

        final Button btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnSignUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    btnSignUp.setBackgroundColor(getResources().getColor(R.color.sonicBackgroundShadow));
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    btnSignUp.setBackgroundColor(getResources().getColor(R.color.sonicBackgroundColor));
                    return true;
                }
                return false;
            }
        });
    }

    public void signUp(View v){
        Intent thisIntent = new Intent(this, Hauptbildschirm.class);
        startActivity(thisIntent);
    }
}
