package com.example.nadirferlin.fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class profilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        final Button btnSave = (Button) findViewById(R.id.btnSave1);

        btnSave.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    btnSave.setBackgroundColor(getResources().getColor(R.color.sonicBackgroundShadow));
                    saveData(btnSave);
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    btnSave.setBackgroundColor(getResources().getColor(R.color.sonicBackgroundColor));
                    return true;
                }
                return false;
            }
        });
    }

    public void saveData(View v) {
        Intent thisIntent = new Intent(this, Hauptbildschirm.class);
        startActivity(thisIntent);
    }
}
