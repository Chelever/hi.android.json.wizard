package com.vijay.jsonwizard.validators.edittext;

import android.app.Dialog;
import android.content.Context;

import com.renan.utils.DatePickerFragment;
import com.rengwuxian.materialedittext.validation.METValidator;

/**
 * Created by renan.pinto on 03,Maio,2017.
 */
public class DateValidator extends METValidator implements DatePickerFragment.OnDateSet {


    private DatePickerFragment.OnDateSet onDateSet;
    private boolean isBirthdate = false;

    public void setBirthdate(boolean birthdate) {
        isBirthdate = birthdate;
    }

    public void setOnDateSet(DatePickerFragment.OnDateSet onDateSet) {
        this.onDateSet = onDateSet;
    }

    public DateValidator(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public boolean isValid(CharSequence charSequence, boolean isEmpty) {
        if (!isEmpty) {
            return true;
        }
        return false;
    }

    public void showDatePickerDialog(Context context) {
        DatePickerFragment fragment = new DatePickerFragment(context);
        fragment.setOnDateSet(this);
        Dialog dialog = fragment.getDatePickerDialog(isBirthdate);
//        dialog.setOnDismissListener(onDialogDismiss);
        dialog.show();
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        if(onDateSet!= null)
            onDateSet.onDateSet(year, month, day);
    }
}
