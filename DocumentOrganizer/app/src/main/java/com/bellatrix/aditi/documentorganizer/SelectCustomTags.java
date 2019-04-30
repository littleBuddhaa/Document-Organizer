package com.bellatrix.aditi.documentorganizer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectCustomTags extends AppCompatActivity {

    private Button backButton, finishButton;
    private TextView textWindow;
    static String ctag;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_custom_tags);
        String textRecognized = getIntent().getExtras().getString("textRecognized");
        final String classname = getIntent().getExtras().getString("classname");
        backButton = (Button) findViewById(R.id.back_button);
        finishButton = (Button) findViewById(R.id.finish_button);
        textWindow = (TextView) findViewById(R.id.tv_tags);
        textWindow.setText(textRecognized);
        ctag=getIntent().getExtras().getString("initialvalue");
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mytag",ctag);
                if(classname.equals("BillsDetailsActivity")) {
                    BillsDetailsActivity.setter(ctag);
                }
                else if(classname.equals("CertificateDetailsActivity")) {
                    CertificateDetailsActivity.setter(ctag);
                }
                else if(classname.equals("GIDDetailsActivity")){
                    GIDDetailsActivity.setter(ctag);
                }
                else if(classname.equals("OtherCategoryDetailsActivity")) {
                    OtherCategoryDetailsActivity.setter(ctag);
                }
                else if(classname.equals("MedicalDetailsActivity")) {
                    MedicalDetailsActivity.setter(ctag);
                }
                else
                {
                    OtherCategoryDetailsActivity.setter(ctag);
                }



                    finish();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
   static void addstring(String a)
    {
        if(ctag.equals(""))
            ;
        else
            ctag+=",";
        ctag+=a;
    }


    }
