package com.renan.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment
                            implements DatePickerDialog.OnDateSetListener {

	public OnDateSet onDateSet;
	public interface OnDateSet{
		void onDateSet(int year, int month, int day);
	}
	
	
	public void setOnDateSet(OnDateSet onDateSet) {
		this.onDateSet = onDateSet;
	}

	private Context context;
    public DatePickerFragment(Context context) {
    	this.context = context;

    }

	
	public Dialog getDatePickerDialog(boolean isBirthdate){
		  final Calendar c = Calendar.getInstance();
	        int year = c.get(Calendar.YEAR);
	        int month = c.get(Calendar.MONTH);
	        int day = c.get(Calendar.DAY_OF_MONTH);

	        DatePickerDialog dialog = new DatePickerDialog(context, this, year, month, day);
		if(isBirthdate)
		dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
	        return dialog;
	}

    public void onDateSet(DatePicker view, int year, int month, int day) {
    	if(onDateSet != null)
    		onDateSet.onDateSet(year, month, day);
    }
}