package com.example.jordan.weather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

public class DialogFragmentAlert extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        Context context = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Opps!!!!!")
                        .setMessage("No Internet connection")
                        .setPositiveButton("OK",null);

        return builder.create();
    }
}
