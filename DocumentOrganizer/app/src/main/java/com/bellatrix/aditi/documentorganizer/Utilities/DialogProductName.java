package com.bellatrix.aditi.documentorganizer.Utilities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.bellatrix.aditi.documentorganizer.R;

public class DialogProductName extends AppCompatDialogFragment {
    private EditText addPname;
    private DialogListenerPName listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_product_name, null);
        builder.setView(view);
        builder.setTitle("Add more Product Name");
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pname = addPname.getText().toString();
                listener.applyTexts2(pname);
            }
        });
        addPname = view.findViewById(R.id.add_product_name);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListenerPName)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogListenerPName");
        }
    }

    public interface DialogListenerPName{
        void applyTexts2(String pname);
    }
}
