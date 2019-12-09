package com.example.asitakipson;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.Locale;

public class DateMask implements TextWatcher {

    private static final int MAX_LENGTH = 8;
    private static final int MIN_LENGTH = 2;

    private String updatedText;
    private boolean editing;


    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

    }

    @Override
    public void onTextChanged(CharSequence text, int start, int before, int count) {
        if (text.toString().equals(updatedText) || editing) return;

        String digits = text.toString().replaceAll("\\D", "");
        int length = digits.length();

        if (length <= MIN_LENGTH) {
            updatedText = digits;
            return;
        }

        if (length > MAX_LENGTH) {
            digits = digits.substring(0, MAX_LENGTH);
        }

        if (length <= 4) {
            String month = digits.substring(0, 2);
            String day = digits.substring(2);

            updatedText = String.format(Locale.US, "%s/%s", month, day);
        }
        else {
            String month = digits.substring(0, 2);
            String day = digits.substring(2, 4);
            String year = digits.substring(4);

            updatedText = String.format(Locale.US, "%s/%s/%s", month, day, year);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

        if (editing) return;

        editing = true;

        editable.clear();
        editable.insert(0, updatedText);

        editing = false;
    }

}