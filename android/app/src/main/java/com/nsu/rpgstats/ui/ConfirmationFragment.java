package com.nsu.rpgstats.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.nsu.rpgstats.R;

public class ConfirmationFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        // TODO: send deletion to server
        builder.setMessage("Are you sure?")
                .setPositiveButton(
                        "Yes",
                        (dialog, id) -> {
                            Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                            Bundle result = new Bundle();
                            result.putString("bundleKey", "del");
                            getParentFragmentManager().setFragmentResult("deleteRequest", result);
                        })
                .setNegativeButton(
                        "No",
                        (dialog, id) -> {
                            Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                            Bundle result = new Bundle();
                            result.putString("bundleKey", "nodel");
                            getParentFragmentManager().setFragmentResult("deleteRequest", result);
                        });
        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirmation, container, false);
    }
}