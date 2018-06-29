package com.example.nadirferlin.fitnesstracker;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
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

/**
 * Liefert dem User alle Informationen, welche er zuvor über sich gespeicher hat.
 * @author Nadir Ferlin, Manuel Dutli
 */
public class ProfileActivity extends AppCompatActivity {

    private DatabaseHelper myDb;
    private EditText editName, editDate, editWeight;
    private Spinner editSpinnerGender, editSpinnerHobby, editSpinnerJob;
    private Button saveButton;
    private TextView showError;
    private ImageButton imageButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String TAG ="ProfileActivity";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editName = (EditText)findViewById(R.id.etName);
        editDate = (EditText)findViewById(R.id.etDate);
        editWeight = (EditText)findViewById(R.id.etWeight);
        editSpinnerJob = (Spinner) findViewById(R.id.spinnerJob);
        editSpinnerHobby = (Spinner) findViewById(R.id.spinnerHobby);
        editSpinnerGender = (Spinner)findViewById(R.id.spinnerGender);
        saveButton = (Button)findViewById(R.id.btnSave);
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
        imageButton = (ImageButton)findViewById(R.id.btnDate);

        saveButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    saveButton.setBackgroundColor(getResources().getColor(R.color.sonicBackgroundShadow));
                    saveData();
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    saveButton.setBackgroundColor(getResources().getColor(R.color.sonicBackgroundColor));
                    return true;
                }
                return false;
            }
        });
        myDb = new DatabaseHelper(this);
        loadUserInformations();
    }

    public void saveData(){
        //Prüft die Eingaben nach der Gültigkeit
        if((editName.getText().toString().equals("")) || (editDate.getText().toString().equals("")) || (editWeight.getText().toString().equals("")) || (editSpinnerJob.getSelectedItem().toString().equals("")) ||
                (editSpinnerHobby.getSelectedItem().toString().equals(""))){
            showError.setVisibility(View.VISIBLE);
        }
        else {
            try {
                Intent thisIntent = new Intent(this, MainPageActivity.class);
                String a = editDate.getText().toString();
                myDb.clearTable(myDb.getDb());
                boolean isInserted = myDb.insertData(editName.getText().toString(), editDate.getText().toString(), editSpinnerGender.getSelectedItemPosition() + "",
                        Double.parseDouble(editWeight.getText().toString()), editSpinnerJob.getSelectedItemPosition() + "", editSpinnerHobby.getSelectedItemPosition() + "");

                if (isInserted == true) {
                    Toast.makeText(ProfileActivity.this, "Daten gespeichert", Toast.LENGTH_SHORT).show();
                    startActivity(thisIntent);
                }
            }catch(Exception ex){
                showError.setVisibility(View.VISIBLE);
                Toast.makeText(ProfileActivity.this, "Daten nicht gespeichert", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
     * Lädt die eingegebenen Informationen von der Datenbank und fügt sie in die Felder ein
     */
    private void loadUserInformations() {
        Cursor res = myDb.getAllData();
        //Prüft pb die DB leer ist, eig. nicht möglich, aber wenn ja kann der Benutzer einfach alles eingeben
        if(res.getCount() == 0){
            return;
        }
        //Geht alle Felder durch und schreibt sie in die
        while(res.moveToNext()){
            editName.setText(res.getString(1));
            editDate.setText(res.getString(2));
            editWeight.setText(res.getString(4));
            editSpinnerJob.setSelection(Integer.parseInt(res.getString(5)));
            editSpinnerHobby.setSelection(Integer.parseInt(res.getString(6)));
            editSpinnerGender.setSelection(Integer.parseInt(res.getString(3)));
        }
    }

    public void openDatePicker(View v){
        DatePickerHelper.openDatePicker(v, this, mDateSetListener);
    }
}
