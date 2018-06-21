package com.example.nadirferlin.fitnesstracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class registrierung extends AppCompatActivity {

    public static boolean registriert;
    DatabaseHelper myDb;
    EditText editName, editAlter, editGewicht, editTaetigkeit, editAktivitaet;
    Spinner spinnerValue;

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
                    signUp((Button) findViewById(R.id.btnSignUp));
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    btnSignUp.setBackgroundColor(getResources().getColor(R.color.sonicBackgroundColor));
                    return true;
                }
                return false;
            }
        });

        editName = (EditText)findViewById(R.id.etName);
        editAlter = (EditText)findViewById(R.id.etAlter);
        editGewicht = (EditText)findViewById(R.id.etGewicht);
        editTaetigkeit = (EditText)findViewById(R.id.etTaetigkeit);
        editAktivitaet = (EditText)findViewById(R.id.etAktivitaet);
        spinnerValue = (Spinner)findViewById(R.id.spinnerGeschlecht);

        myDb = new DatabaseHelper(this);
    }

    public void signUp(View v){
        Intent thisIntent = new Intent(this, Hauptbildschirm.class);
        startActivity(thisIntent);

        boolean isInserted = myDb.insertData(editName.getText().toString(), editAlter.getText().toString(), spinnerValue.getSelectedItem().toString(),
                Double.parseDouble(editGewicht.getText().toString()), editTaetigkeit.getText().toString(), editAktivitaet.getText().toString());

        if(isInserted == true){
            Toast.makeText(registrierung.this, "Data inserted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(registrierung.this, "Data not inserted", Toast.LENGTH_SHORT).show();
        }
    }
}
