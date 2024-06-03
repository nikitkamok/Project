package com.example.planegame;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

public class StatsDialog extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.CustomDialogTheme);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog2, null);
        builder.setView(view)
                .setTitle("Stats")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                });

        final AlertDialog dialog = builder.create();

        // Use dialog.setOnShowListener to customize buttons after they are created
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

                // Apply custom styles to the buttons
                positiveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink));
            }
        });

        return dialog;
    }
    @Override
    public void onStart() {
        super.onStart();
        // Set the width and height of the dialog
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = 900;
            int height = 450;
            dialog.getWindow().setLayout(width, height);
        }
    }
}