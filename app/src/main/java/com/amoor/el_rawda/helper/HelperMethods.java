package com.amoor.el_rawda.helper;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Spinner;

public class HelperMethods {

    public static void replace(Fragment fragment, FragmentManager supportFragmentManager, int id, String tag) {
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace(id, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void replace(Fragment fragment, FragmentManager supportFragmentManager, int id) {
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static String getTextFromTil(TextInputLayout textInputLayout) {
        String text = textInputLayout.getEditText().getText().toString().trim();
        return text;
    }

    public static void setTexttoTil(TextInputLayout textInputLayout, String text) {
        textInputLayout.getEditText().setText(text);
    }

    public static String getTextFromSpinner(Spinner spinner) {
        String text = spinner.getSelectedItem().toString().trim();
        return text;
    }
}
