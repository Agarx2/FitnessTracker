package com.example.nadirferlin.fitnesstracker;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class registrierung extends AppCompatActivity {

    private DatabaseHelper myDb;
    private EditText editName, editDatum, editGewicht;
    private Spinner editSpinnerGeschlecht, editSpinnerTaetigkeit, editSpinnerHobby;
    private ImageButton dateButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String TAG ="registrierung";
    private Intent thisIntent;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDb = new DatabaseHelper(this);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registrierung);

            final Button btnSignUp = (Button) findViewById(R.id.btnSignUp);

            btnSignUp.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        btnSignUp.setBackgroundColor(getResources().getColor(R.color.sonicBackgroundShadow));
                        signUp(btnSignUp);
                        return true;
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        btnSignUp.setBackgroundColor(getResources().getColor(R.color.sonicBackgroundColor));
                        return true;
                    }
                    return false;
                }
            });

            editName = (EditText)findViewById(R.id.etName);
            editDatum = (EditText)findViewById(R.id.etDatum);
            editGewicht = (EditText)findViewById(R.id.etGewicht);
            editSpinnerTaetigkeit = (Spinner)findViewById(R.id.spinnerTaetigkeit);
            editSpinnerHobby = (Spinner)findViewById(R.id.spinnerHobby);
            editSpinnerGeschlecht = (Spinner)findViewById(R.id.spinnerGeschlecht);
            dateButton = (ImageButton)findViewById(R.id.btnDate);

    }

    public void signUp(View v){
        try {
            Intent thisIntent = new Intent(this, Hauptbildschirm.class);
            String a = editDatum.getText().toString();
            boolean isInserted = myDb.insertData(editName.getText().toString(), editDatum.getText().toString(), editSpinnerGeschlecht.getSelectedItemPosition() + "",
                    Double.parseDouble(editGewicht.getText().toString()), editSpinnerTaetigkeit.getSelectedItemPosition() + "", editSpinnerHobby.getSelectedItemPosition() + "");

            if (isInserted == true) {
                Toast.makeText(registrierung.this, "Daten gespeichert", Toast.LENGTH_SHORT).show();
                startActivity(thisIntent);
            }
        }catch(Exception ex){
            Toast.makeText(registrierung.this, "Daten nicht gespeichert", Toast.LENGTH_SHORT).show();
        }
    }

    public void openDatePicker(View v){
        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                registrierung.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = month +"/" + day + "/" + year;
                editDatum.setText(date);
            }
        };
    }
}
