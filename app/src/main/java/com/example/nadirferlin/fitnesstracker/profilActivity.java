package com.example.nadirferlin.fitnesstracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class profilActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editAlter, editGewicht, editTaetigkeit, editAktivitaet;
    Spinner spinnerValue;
    Button saveButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        editName = (EditText)findViewById(R.id.etName);
        editAlter = (EditText)findViewById(R.id.etDatum);
        editGewicht = (EditText)findViewById(R.id.etGewicht);
        editTaetigkeit = (EditText)findViewById(R.id.etTaetigkeit);
        editAktivitaet = (EditText)findViewById(R.id.etAktivitaet);
        spinnerValue = (Spinner)findViewById(R.id.spinnerGeschlecht);
        saveButton = (Button)findViewById(R.id.btnSave);

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
        try{
            Intent thisIntent = new Intent(this, Hauptbildschirm.class);
            startActivity(thisIntent);

            boolean isInserted = myDb.insertData(editName.getText().toString(), editAlter.getText().toString(), spinnerValue.getSelectedItem().toString(),
                    Double.parseDouble(editGewicht.getText().toString()), editTaetigkeit.getText().toString(), editAktivitaet.getText().toString());

            if(isInserted == true){
                Toast.makeText(profilActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception ex){
            Toast.makeText(profilActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
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
            editAlter.setText(res.getString(2));
            editGewicht.setText(res.getString(4));
            editTaetigkeit.setText(res.getString(5));
            editAktivitaet.setText(res.getString(6));

            String a = res.getString(3);
            if("Männlich".equals(res.getString(3))){
                spinnerValue.setSelection(0);
            }else{
                spinnerValue.setSelection(1);
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("a");
        builder.setMessage(buffer.toString());
    }
}
