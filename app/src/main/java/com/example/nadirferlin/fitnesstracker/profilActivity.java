package com.example.nadirferlin.fitnesstracker;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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

public class profilActivity extends AppCompatActivity {

    private DatabaseHelper myDb;
    private EditText editName, editDatum, editGewicht;
    private Spinner editSpinnerGeschlecht, editSpinnerHobby, editSpinnerTaetigkeit;
    private Button saveButton;
    private TextView showFehlermeldung;
    private ImageButton imageButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String TAG ="profilActivity";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        editName = (EditText)findViewById(R.id.etName);
        editDatum = (EditText)findViewById(R.id.etDatum);
        editGewicht = (EditText)findViewById(R.id.etGewicht);
        editSpinnerTaetigkeit = (Spinner) findViewById(R.id.spinnerTaetigkeit);
        editSpinnerHobby = (Spinner) findViewById(R.id.spinnerHobby);
        editSpinnerGeschlecht = (Spinner)findViewById(R.id.spinnerGeschlecht);
        saveButton = (Button)findViewById(R.id.btnSave);
        showFehlermeldung = (TextView)findViewById(R.id.tvFehlermeldung);
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);
                String date = day +"." + month + "." + year;
                editDatum.setText(date);
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
        if((editName.getText().toString().equals("")) || (editDatum.getText().toString().equals("")) || (editGewicht.getText().toString().equals("")) || (editSpinnerTaetigkeit.getSelectedItem().toString().equals("")) ||
                (editSpinnerHobby.getSelectedItem().toString().equals(""))){
            showFehlermeldung.setVisibility(View.VISIBLE);
        }
        else {
            try {
                Intent thisIntent = new Intent(this, Hauptbildschirm.class);
                String a = editDatum.getText().toString();
                boolean isInserted = myDb.insertData(editName.getText().toString(), editDatum.getText().toString(), editSpinnerGeschlecht.getSelectedItemPosition() + "",
                        Double.parseDouble(editGewicht.getText().toString()), editSpinnerTaetigkeit.getSelectedItemPosition() + "", editSpinnerHobby.getSelectedItemPosition() + "");

                if (isInserted == true) {
                    Toast.makeText(profilActivity.this, "Daten gespeichert", Toast.LENGTH_SHORT).show();
                    startActivity(thisIntent);
                }
            }catch(Exception ex){
                showFehlermeldung.setVisibility(View.VISIBLE);
                Toast.makeText(profilActivity.this, "Daten nicht gespeichert", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadUserInformations() {
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0){
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            editName.setText(res.getString(1));
            editDatum.setText(res.getString(2));
            editGewicht.setText(res.getString(4));
            editSpinnerTaetigkeit.setSelection(Integer.parseInt(res.getString(5)));
            editSpinnerHobby.setSelection(Integer.parseInt(res.getString(6)));
            editSpinnerGeschlecht.setSelection(Integer.parseInt(res.getString(3)));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("a");
        builder.setMessage(buffer.toString());
    }

    public void openDatePicker(View v){
        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                profilActivity.this,
                android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
