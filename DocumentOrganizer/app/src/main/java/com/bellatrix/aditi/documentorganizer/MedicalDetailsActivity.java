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
import com.bellatrix.aditi.documentorganizer.Utilities.DialogProductType;

import java.util.Collections;

import static com.bellatrix.aditi.documentorganizer.Utilities.Constants.BNR_SUB_CATEGORIES_1;
import static com.bellatrix.aditi.documentorganizer.Utilities.Constants.MEDICAL_SUB_CATEGORIES_1;
import static java.sql.Types.NULL;

public class MedicalDetailsActivity extends AppCompatActivity {

    private static final String TAG = MedicalDetailsActivity.class.getSimpleName();
    private static final int ADD_DETAILS_RESULT_CODE = 50;
    private byte[] img;
    private final String folderName = "Medical_records";

    private static EditText issuedDate, imageTitle, patientName, institution, customTags;
    private ImageButton datePicker;
    private RadioGroup radioGroup;
    private Button backButton, finishButton, addRecordType,addCustomTags;
    private Uri uri;
    static String  ctag;
    String textRecognized;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_details);

        uri = Uri.parse(getIntent().getExtras().getString("imageUri"));
        textRecognized = CommonFunctions.getTextFromUri(this, uri, TAG);
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
        customTags = (EditText)findViewById(R.id.et_custom_tags);
        addRecordType = (Button) findViewById(R.id.btn_record_type);
        addCustomTags = (Button) findViewById(R.id.btn_custom_tags);
        setRadioGroup();

        String title = folderName+"_"
                +String.valueOf(DBQueries.getTotalImageByFolder(this, folderName)+1);
        imageTitle.setText(title);

        addCustomTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalDetailsActivity.this, SelectCustomTags.class);
                intent.putExtra("textRecognized", textRecognized);
                intent.putExtra("classname","MedicalDetailsActivity");
                startActivity(intent);


            }
        });

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

        addRecordType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogReportType();

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
    public static void setter(String s)
    {
        ctag = s;
        customTags.setText(ctag);
    }
    public void openDialogReportType()
    {
        DialogProductType dpt = new DialogProductType();
        dpt.show(getSupportFragmentManager(), "Adding Record Type");
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
                patientName.getText().toString(),institution.getText().toString(),customTags.getText().toString());
    }

    private void setRadioGroup() {
        Collections.sort(MEDICAL_SUB_CATEGORIES_1);
        for(String type: MEDICAL_SUB_CATEGORIES_1) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(type);
            radioGroup.addView(radioButton);
        }
    }

    private void setRadioGroup1(String str) {
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(str);
        radioGroup.addView(radioButton);
    }

    public void applyTexts1(String ptype) {
        // addMoreProductType.setText(ptype); //only for see if correct value is being received
        BNR_SUB_CATEGORIES_1.add(ptype);
        setRadioGroup1(ptype);
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
