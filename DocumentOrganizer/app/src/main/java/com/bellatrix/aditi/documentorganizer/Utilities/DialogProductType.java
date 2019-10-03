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

public class DialogProductType extends AppCompatDialogFragment {
    private EditText addPtype;
    private DialogListenerPType listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_product_type, null);
        builder.setView(view);
        builder.setTitle("Add more Product Type");
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ptype = addPtype.getText().toString();
                listener.applyTexts1(ptype);
            }
        });
        addPtype = view.findViewById(R.id.add_product_type);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListenerPType)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DialogListenerPType");
        }
    }

    public interface DialogListenerPType{
        void applyTexts1(String ptype);
    }
}

