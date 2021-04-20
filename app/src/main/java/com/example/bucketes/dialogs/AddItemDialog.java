package com.example.bucketes.dialogs;


import android.content.Context;
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
import com.example.bucketes.models.Item;

public class AddItemDialog extends AppCompatDialogFragment {
    private CustomDialogListener listener;
    private TextView txtTitle, tvErrorMsg;

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_item_dialog, null);


        txtTitle = view.findViewById(R.id.editTitle);

        builder.setView(view)
                .setTitle("Bucketes App")
            .setMessage("Enter title of the item")
            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String itemTitle = txtTitle.getText().toString();
                    listener.addItem(itemTitle);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // automatically closes dialog
                }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (CustomDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement CustomDialogListener");
        }
    }

    public interface CustomDialogListener {
        void addItem(String title);
    }
}
