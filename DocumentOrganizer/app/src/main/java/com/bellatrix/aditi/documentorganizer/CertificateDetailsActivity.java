package com.bellatrix.aditi.documentorganizer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bellatrix.aditi.documentorganizer.Database.DBQueries;
import com.bellatrix.aditi.documentorganizer.Utilities.CommonFunctions;

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
    private RadioGroup radioGroup1, radioGroup2;
    private RadioButton radioButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_details);

        Uri uri = Uri.parse(getIntent().getExtras().getString("imageUri"));
        int quality = getIntent().getExtras().getInt("imageQuality");
        img = CommonFunctions.uriToBitmap(this,uri,TAG,quality);

        imageTitle = (EditText)findViewById(R.id.et_image_title);
        holderName = (EditText)findViewById(R.id.et_holder_name);
        institution = (EditText)findViewById(R.id.et_institution);
        achievement = (EditText)findViewById(R.id.et_achievement);

        radioGroup1 = (RadioGroup)findViewById(R.id.radio_grp1);
        radioButton2 = (RadioButton)findViewById(R.id.radio_marksheet);
        type2 = (LinearLayout) findViewById(R.id.ll_type2);
        radioGroup2 = (RadioGroup)findViewById(R.id.radio_grp2);
        backButton = (Button)findViewById(R.id.back_button);
        finishButton = (Button)findViewById(R.id.finish_button);

        radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    ((TextView)findViewById(R.id.tv_type2)).setVisibility(View.VISIBLE);
                    type2.setVisibility(View.VISIBLE);
                } else {
                    ((TextView)findViewById(R.id.tv_type2)).setVisibility(View.GONE);
                    type2.setVisibility(View.GONE);
                }
            }
        });

        setRadioGroup();

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
        String val1=((RadioButton)findViewById(radioGroup1.getCheckedRadioButtonId())).getText().toString();
        String val2="";

        if(val1.equals("Marksheet")) {
            val2=((RadioButton)findViewById(radioGroup2.getCheckedRadioButtonId())).getText().toString();
        }

        DBQueries.insertCertificate(CertificateDetailsActivity.this,
                id,val1,val2,
                holderName.getText().toString(),institution.getText().toString(),
                achievement.getText().toString());

        Intent intent = new Intent(CertificateDetailsActivity.this, ViewImageActivity.class);
        intent.putExtra("folderName", folderName);
        startActivity(intent);
    }

    private void setRadioGroup() {
        Collections.sort(CERTIFICATE_SUB_CATEGORIES_2);
        for(String type: CERTIFICATE_SUB_CATEGORIES_2) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(type);
            radioGroup2.addView(radioButton);
        }
    }
}
