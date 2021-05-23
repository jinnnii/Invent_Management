package com.example.user.ncpaidemo;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class ViewHolder {
    public static class WideUserItemHolder {
        EditText name;
        EditText unit_price;
        EditText count;
        EditText nDay;
        EditText unit_amount;
        EditText sCategory;
        Spinner unit;
        Spinner lCategory;
        int ref;
    }

    public static class ViewHolderW {
        EditText count;
        EditText amount;
        TextView sCategory;
        TextView unit;
        int ref;
    }

}

