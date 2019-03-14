package com.bellatrix.aditi.documentorganizer;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bellatrix.aditi.documentorganizer.Database.DBQueries;

import java.util.regex.Pattern;

/**
 * Created by Aditi on 14-03-2019.
 */

public class AddFolderDialogFragment extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("New Folder");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_add_folder, null))
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddFolderDialogFragment.this.getDialog().cancel();
                    }
                });
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        String folderName =  ((EditText) AddFolderDialogFragment.this.getDialog()
                                .findViewById(R.id.et_folder_name))
                                .getText().toString();
                        String color = ((EditText) AddFolderDialogFragment.this.getDialog()
                                .findViewById(R.id.et_color))
                                .getText().toString();
                        if(!Pattern.compile("#[a-fA-F0-9]{6}").matcher(color).matches()||folderName.equals("")) {
                            Toast.makeText(getActivity(),"Invalid input",Toast.LENGTH_SHORT).show();
                        } else {
                            DBQueries.insertFolder(getActivity(),folderName,color);
                            AddFolderDialogFragment.this.getDialog().dismiss();

                        }
                    }
                });
            }
        });

        return mAlertDialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity parentFragment = getActivity();
        if (parentFragment instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) parentFragment).onDismiss(dialog);
        }
    }
}