package com.bellatrix.aditi.documentorganizer;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bellatrix.aditi.documentorganizer.Database.DBQueries;
import com.bellatrix.aditi.documentorganizer.Utilities.CommonFunctions;
import com.bellatrix.aditi.documentorganizer.Utilities.DateUtil;

import java.util.ArrayList;
import java.util.Collections;

import static com.bellatrix.aditi.documentorganizer.Utilities.Constants.MEDICAL_SUB_CATEGORIES_1;

public class MedicalDetailsActivity extends AppCompatActivity {

    private static final String TAG = AddImageActivity.class.getSimpleName();
    private static final int ADD_DETAILS_RESULT_CODE = 50;
    private byte[] img;
    private final String folderName = "Medical records";

    private EditText issuedDate, imageTitle, patientName, institution;
    private ImageButton datePicker;
    private LinearLayout type1;
    private Button backButton, finishButton;
    private ArrayList<CheckBox> checkBox1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_details);

        Uri uri = Uri.parse(getIntent().getExtras().getString("imageUri"));
        img = CommonFunctions.uriToBitmap(this,uri,TAG);

        issuedDate = (EditText)findViewById(R.id.et_issued_date);
        imageTitle = (EditText)findViewById(R.id.et_image_title);
        patientName = (EditText)findViewById(R.id.et_patient_name);
        institution = (EditText)findViewById(R.id.et_institution);
        datePicker = (ImageButton)findViewById(R.id.date_picker_button);
        type1 = (LinearLayout) findViewById(R.id.ll_type);
        backButton = (Button)findViewById(R.id.back_button);
        finishButton = (Button)findViewById(R.id.finish_button);

        checkBox1 = new ArrayList<>();
        setCheckBoxes();

        String title = folderName+"_"
                +String.valueOf(DBQueries.getTotalImageByFolder(this, folderName)+1);
        imageTitle.setText(title);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.show(getSupportFragmentManager(), "datePicker");

                try
                {
                    DatePickerFragment newFragment = (DatePickerFragment) dateFragment;
                    newFragment.setClass(MedicalDetailsActivity.this);
                }
                catch (ClassCastException e)
                {
                    e.printStackTrace();
                }
            }
        });

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
        long id = DBQueries.insertDocument(MedicalDetailsActivity.this,img,
                imageTitle.getText().toString(),folderName);

        // insertion in the table for the folder
        String val="";
        for(CheckBox checkBox: checkBox1) {
            if(checkBox.isChecked()) {
                val=val+MEDICAL_SUB_CATEGORIES_1.get(checkBox1.indexOf(checkBox))+",";
            }
        }
        if(val.length()>1)
            val = val.substring(0,val.length()-1);

        DBQueries.insertMedical(MedicalDetailsActivity.this,
                id,issuedDate.getText().toString(),val,
                patientName.getText().toString(),institution.getText().toString());

        Intent intent = new Intent(MedicalDetailsActivity.this, ViewImageActivity.class);
        intent.putExtra("folderName", folderName);
        startActivity(intent);
    }

    private void setCheckBoxes() {
        Collections.sort(MEDICAL_SUB_CATEGORIES_1);
        for(String type: MEDICAL_SUB_CATEGORIES_1) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(type);
            type1.addView(checkBox);
            checkBox1.add(checkBox);
        }
    }

    public void setDate(DateUtil date) {
        String day = String.valueOf(date.getDay());
        String month = String.valueOf(date.getMonth());
        String year = String.valueOf(date.getYear());
        if(day.length()==1) day="0"+day;
        if(month.length()==1) month="0"+month;
        issuedDate.setText(year+"-"+month+"-"+day);
    }
}
