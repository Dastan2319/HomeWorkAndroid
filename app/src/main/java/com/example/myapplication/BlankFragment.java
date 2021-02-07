package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.preference.DialogPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BlankFragment extends DialogFragment {
    private addName add;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        add = (addName) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        EditText input = new EditText( getContext());
        return builder
                .setTitle("Enter notification message")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences prefs = getActivity().getSharedPreferences("main", 0);

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("name", input.getText().toString());
                        editor.apply();
                        Toast.makeText(getContext(),input.getText().toString(),Toast.LENGTH_LONG).show();
                        add.add(input.getText().toString());
//                        removable.remove(phone);
                    }
                })
                .setNegativeButton("Отмена", null)
                .create();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
    }
}