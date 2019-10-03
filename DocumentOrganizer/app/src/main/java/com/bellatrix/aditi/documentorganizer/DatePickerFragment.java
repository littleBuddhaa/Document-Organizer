package com.bellatrix.aditi.documentorganizer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.bellatrix.aditi.documentorganizer.Utilities.DateUtil;

import java.util.Calendar;

/**
 * Created by Aditi on 27-03-2019.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private DateUtil date;
    private BillsDetailsActivity aClass;
    private MedicalDetailsActivity bClass;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void setClass(BillsDetailsActivity aClass)
    {
        this.aClass = aClass;
    }
    public void setClass(MedicalDetailsActivity bClass)
    {
        this.bClass = bClass;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        date = new DateUtil(year, month+1, day);
        if(aClass!=null)
            aClass.setDate(date);
        else
            bClass.setDate(date);
    }
}