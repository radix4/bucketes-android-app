package com.example.bucketes.dialogs;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.bucketes.R;

public class SameTitleErrorDialog extends AppCompatDialogFragment {
    private TextView txtTitle;

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_item_dialog, null);


        txtTitle = view.findViewById(R.id.editTitle);
        txtTitle.setVisibility(view.INVISIBLE);

        builder.setView(view)
                .setTitle("Bucketes App")
                .setMessage("Error! Item is already in the list.")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // automatically closes dialog
                    }
                });

        return builder.create();
    }
}
