package com.nsu.rpgstats.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.nsu.rpgstats.R;

public class ConfirmationFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // TODO: send deletion to server
        builder.setMessage("Are you sure?")
                .setPositiveButton("Yes", (dialog, id) ->
                        Toast.makeText(getContext(), "Deleted (no)", Toast.LENGTH_SHORT).show())
                .setNegativeButton("No", (dialog, id) ->
                        Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show());
        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirmation, container, false);
    }
}