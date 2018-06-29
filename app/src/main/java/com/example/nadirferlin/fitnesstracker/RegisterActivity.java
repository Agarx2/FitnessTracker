package com.example.nadirferlin.fitnesstracker;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/*
 * Speichert die ersten eingaben des Benutzers ein
 */
public class RegisterActivity extends AppCompatActivity {

    private DatabaseHelper myDb;
    private EditText editName, editDate, editWeight;
    private Spinner editSpinnerGender, editSpinnerJob, editSpinnerHobby;
    private ImageButton dateButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String TAG ="RegisterActivity";
    private TextView showError;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDb = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
            editDate = (EditText)findViewById(R.id.etDate);
            editWeight = (EditText)findViewById(R.id.etWeight);
            editSpinnerJob = (Spinner)findViewById(R.id.spinnerJob);
            editSpinnerHobby = (Spinner)findViewById(R.id.spinnerHobby);
            editSpinnerGender = (Spinner)findViewById(R.id.spinnerGender);
            dateButton = (ImageButton)findViewById(R.id.btnDate);
            showError = (TextView)findViewById(R.id.tvError);

            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1;
                    Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);
                    String date = day +"." + month + "." + year;
                    editDate.setText(date);
                }
            };
    }

    /**
     * Methode berechnet die anzahl Kalorien für die zurückgelegte Distanz
     * @param v - representiert den Button
     * @return gibt die anzahl Kalorien als String zurück, auf 2 Dezimalstellen genau
     */
    public void signUp(View v){
        if((editName.getText().toString().equals("")) || (editDate.getText().toString().equals("")) || (editWeight.getText().toString().equals("")) || (editSpinnerJob.getSelectedItem().toString().equals("")) ||
            (editSpinnerHobby.getSelectedItem().toString().equals(""))){

            showError.setVisibility(View.VISIBLE);
        }
        else{
            try {
                Intent thisIntent = new Intent(this, MainPageActivity.class);
                String a = editDate.getText().toString();
                myDb.clearTable(myDb.getDb());
                boolean isInserted = myDb.insertData(editName.getText().toString(), editDate.getText().toString(), editSpinnerGender.getSelectedItemPosition() + "",
                        Double.parseDouble(editWeight.getText().toString()), editSpinnerJob.getSelectedItemPosition() + "", editSpinnerHobby.getSelectedItemPosition() + "");

                if (isInserted == true) {
                    Toast.makeText(RegisterActivity.this, "Daten gespeichert", Toast.LENGTH_SHORT).show();
                    startActivity(thisIntent);
                }
            }catch(Exception ex){
              Toast.makeText(RegisterActivity.this, "Daten nicht gespeichert", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openDatePicker(View v){
        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                RegisterActivity.this,
                android.R.style.Theme_Material_Dialog,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sonicBackgroundShadow)));

        dialog.show();
    }
}
