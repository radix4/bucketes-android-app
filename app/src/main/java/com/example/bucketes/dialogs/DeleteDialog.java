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

public class DeleteDialog extends AppCompatDialogFragment {
    private CustomDialogListener listener;
    private TextView txtTitle;
    private String title;

    public DeleteDialog(String title) {
        super();
        this.title = title;
    }

    public String getTitleForDeletion() {
        return title;
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.main_row, null);

        txtTitle = view.findViewById(R.id.txtItemTitle);

        builder.setTitle("Bucketes App")
                .setMessage("Are you sure you want to delete '" + getTitleForDeletion() + "' ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.deleteItem(getTitleForDeletion());
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
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
        void deleteItem(String title);
    }
}
