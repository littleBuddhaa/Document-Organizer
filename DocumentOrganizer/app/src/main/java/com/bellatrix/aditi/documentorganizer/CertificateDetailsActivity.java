package com.bellatrix.aditi.documentorganizer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bellatrix.aditi.documentorganizer.Database.DBQueries;
import com.bellatrix.aditi.documentorganizer.Utilities.CommonFunctions;

import java.util.ArrayList;
import java.util.Collections;

import static com.bellatrix.aditi.documentorganizer.Utilities.Constants.CERTIFICATE_SUB_CATEGORIES_2;

public class CertificateDetailsActivity extends AppCompatActivity {

    private static final String TAG = CertificateDetailsActivity.class.getSimpleName();
    private static final int ADD_DETAILS_RESULT_CODE = 50;
    private byte[] img;
    private final String folderName = "Certificates & Marksheets";

    private EditText imageTitle, holderName, institution, achievement;
    private LinearLayout type2;
    private Button backButton, finishButton;
    private ArrayList<CheckBox> checkBox1;
    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_details);

        Uri uri = Uri.parse(getIntent().getExtras().getString("imageUri"));
        img = CommonFunctions.uriToBitmap(this,uri,TAG);

        imageTitle = (EditText)findViewById(R.id.et_image_title);
        holderName = (EditText)findViewById(R.id.et_holder_name);
        institution = (EditText)findViewById(R.id.et_institution);
        achievement = (EditText)findViewById(R.id.et_achievement);

        radioGroup = (RadioGroup)findViewById(R.id.radio_grp);
        radioButton1 = (RadioButton)findViewById(R.id.radio_certificate);
        radioButton2 = (RadioButton)findViewById(R.id.radio_marksheet);
        type2 = (LinearLayout) findViewById(R.id.ll_type2);
        backButton = (Button)findViewById(R.id.back_button);
        finishButton = (Button)findViewById(R.id.finish_button);

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView)findViewById(R.id.tv_type2)).setVisibility(View.GONE);
                type2.setVisibility(View.GONE);
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView)findViewById(R.id.tv_type2)).setVisibility(View.VISIBLE);
                type2.setVisibility(View.VISIBLE);
            }
        });

        checkBox1 = new ArrayList<>();
        setCheckBoxes();

        String title = folderName+"_"
                +String.valueOf(DBQueries.getTotalImageByFolder(this, folderName)+1);
        imageTitle.setText(title);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleData();
                setResult(ADD_DETAILS_RESULT_CODE);
                finish();
            }
        });
    }

    private void handleData() {

        // insertion in global table
        long id = DBQueries.insertDocument(CertificateDetailsActivity.this,img,
                imageTitle.getText().toString(),folderName);

        // insertion in the table for the folder
        String val1=((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
        String val2="";

        if(val1.equals("Marksheet")) {
            for(CheckBox checkBox: checkBox1) {
                if(checkBox.isChecked()) {
                    val2=val2+CERTIFICATE_SUB_CATEGORIES_2.get(checkBox1.indexOf(checkBox))+",";
                }
            }
            if(val2.length()>1)
                val2 = val2.substring(0,val2.length()-1);
        }

        DBQueries.insertCertificate(CertificateDetailsActivity.this,
                id,val1,val2,
                holderName.getText().toString(),institution.getText().toString(),
                achievement.getText().toString());

        Intent intent = new Intent(CertificateDetailsActivity.this, ViewImageActivity.class);
        intent.putExtra("folderName", folderName);
        startActivity(intent);
    }

    private void setCheckBoxes() {
        Collections.sort(CERTIFICATE_SUB_CATEGORIES_2);
        for(String type: CERTIFICATE_SUB_CATEGORIES_2) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(type);
            type2.addView(checkBox);
            checkBox1.add(checkBox);
        }
    }
}
