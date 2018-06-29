package com.example.nadirferlin.fitnesstracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import java.util.Calendar;

/**
 * Öffnet ein Dialog welcher einem ermöglicht aus einem Kalender sein Geburtsdatum auszuwählen
 * @author Manuel Dutli
 */
public class DatePickerHelper {

    public static void openDatePicker(View v, Activity act, DatePickerDialog.OnDateSetListener listener){
        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        //Erstellt einen neuen Dialog, in welchem der User das Datum auswählen kann
        DatePickerDialog dialog = new DatePickerDialog(
                act,
                android.R.style.Theme_Material_Dialog,
                listener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(act.getResources().getColor(R.color.sonicBackgroundShadow)));

        dialog.show();
    }
}
