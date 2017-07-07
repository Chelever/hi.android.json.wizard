package com.vijay.jsonwizard.validators.edittext;

import com.renan.utils.FieldValidation;
import com.rengwuxian.materialedittext.validation.METValidator;

/**
 * Created by renan.pinto on 03,Maio,2017.
 */
public class CPFValidator extends METValidator {


    public CPFValidator(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public boolean isValid(CharSequence charSequence, boolean isEmpty) {
        System.out.println("New value in cpf "+charSequence.toString());
        if(!isEmpty)
            return FieldValidation.isCPF(charSequence.toString());
            return false;
    }
}
