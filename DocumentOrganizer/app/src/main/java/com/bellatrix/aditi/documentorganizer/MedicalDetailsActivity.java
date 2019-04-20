package com.bellatrix.aditi.documentorganizer;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bellatrix.aditi.documentorganizer.Database.DBQueries;
import com.bellatrix.aditi.documentorganizer.Utilities.CommonFunctions;
import com.bellatrix.aditi.documentorganizer.Utilities.DateUtil;

import java.util.Collections;

import static com.bellatrix.aditi.documentorganizer.Utilities.Constants.MEDICAL_SUB_CATEGORIES_1;
import static java.sql.Types.NULL;

public class MedicalDetailsActivity extends AppCompatActivity {

    private static final String TAG = MedicalDetailsActivity.class.getSimpleName();
    private static final int ADD_DETAILS_RESULT_CODE = 50;
    private byte[] img;
    private final String folderName = "Medical_records";

    private EditText issuedDate, imageTitle, patientName, institution;
    private ImageButton datePicker;
    private RadioGroup radioGroup;
    private Button backButton, finishButton;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_details);

        uri = Uri.parse(getIntent().getExtras().getString("imageUri"));
        int quality = getIntent().getExtras().getInt("imageQuality");
        img = CommonFunctions.uriToBytes(this,uri,TAG,quality);

        issuedDate = (EditText)findViewById(R.id.et_issued_date);
        imageTitle = (EditText)findViewById(R.id.et_image_title);
        patientName = (EditText)findViewById(R.id.et_patient_name);
        institution = (EditText)findViewById(R.id.et_institution);
        datePicker = (ImageButton)findViewById(R.id.date_picker_button);
        radioGroup = (RadioGroup)findViewById(R.id.radio_grp);
        backButton = (Button)findViewById(R.id.back_button);
        finishButton = (Button)findViewById(R.id.finish_button);

        setRadioGroup();

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
                Intent intent = new Intent(MedicalDetailsActivity.this, ViewImageActivity.class);
                intent.putExtra("folderName", folderName);
                startActivity(intent);
                finish();
            }
        });
    }

    private void handleData() {

        // insertion in global table
        long id = DBQueries.insertDocument(MedicalDetailsActivity.this,NULL,img,
                imageTitle.getText().toString(),folderName);

        // insertion in the table for the folder
        String val="";
        RadioButton button;
        if((button=(RadioButton)findViewById(radioGroup.getCheckedRadioButtonId()))!=null)
                val = button.getText().toString();

        DBQueries.insertMedical(MedicalDetailsActivity.this,
                id,issuedDate.getText().toString(),val,
                patientName.getText().toString(),institution.getText().toString());
    }

    private void setRadioGroup() {
        Collections.sort(MEDICAL_SUB_CATEGORIES_1);
        for(String type: MEDICAL_SUB_CATEGORIES_1) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(type);
            radioGroup.addView(radioButton);
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
