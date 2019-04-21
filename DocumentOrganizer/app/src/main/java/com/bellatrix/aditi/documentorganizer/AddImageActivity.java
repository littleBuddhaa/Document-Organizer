package com.bellatrix.aditi.documentorganizer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bellatrix.aditi.documentorganizer.Database.Contract;
import com.bellatrix.aditi.documentorganizer.Database.DBQueries;
import com.bellatrix.aditi.documentorganizer.Utilities.CommonFunctions;

import java.util.ArrayList;

import static com.bellatrix.aditi.documentorganizer.Utilities.Constants.COLORS;
import static com.bellatrix.aditi.documentorganizer.Utilities.Constants.colorIndex;

public class AddImageActivity extends AppCompatActivity{

    private static final String TAG = AddImageActivity.class.getSimpleName();
    private static final int ADD_DETAILS_REQUEST = 1;
    private static final int ADD_DETAILS_RESULT_CODE = 50;

    private Uri imageUri;
    private byte[] img;
    private String folderName;
    private ArrayList<String> folderNames;
    private int spinnerPosition;
    private Cursor folderCursor;

    private ImageView imageView;
    private EditText custom_folder_name, customQuality;
    private RadioGroup radioGroup;
    private RadioButton radioCustom;
    private Spinner folderSpinner;
    private Button cancelButton, nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        imageUri = Uri.parse(getIntent().getExtras().getString("imageUri"));
        img = CommonFunctions.uriToBytes(this,imageUri,TAG,30);

        imageView = (ImageView)findViewById(R.id.imagebox);
        customQuality = (EditText)findViewById(R.id.et_custom_quality);
        custom_folder_name = (EditText)findViewById(R.id.et_folder_name);
        radioGroup = (RadioGroup)findViewById(R.id.radio_grp);
        radioCustom = (RadioButton) findViewById(R.id.radio_custom);
        folderSpinner = (Spinner)findViewById(R.id.spinner_folder);
        cancelButton = (Button)findViewById(R.id.cancel_button);
        nextButton = (Button)findViewById(R.id.next_button);

        imageView.setImageBitmap(BitmapFactory.decodeByteArray(img, 0 , img.length));

        radioCustom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    customQuality.setVisibility(View.VISIBLE);
                } else {
                    customQuality.setVisibility(View.GONE);
                }
            }
        });

        folderNames = new ArrayList<String>();
        folderCursor = DBQueries.getFolders(this);

        while (folderCursor.moveToNext()) {
            folderNames.add(folderCursor.getString(folderCursor.getColumnIndex(Contract.Folders.COLUMN_FOLDER_NAME)));
        }
        folderNames.add("Add custom folder");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                folderNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        folderSpinner.setAdapter(adapter);

        folderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerPosition=position;
                if(position==(folderNames.size()-1))
                    custom_folder_name.setVisibility(View.VISIBLE);
                else {
                    folderName = String.valueOf(parent.getItemAtPosition(position));
                    custom_folder_name.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinnerPosition==(folderNames.size()-1)){
                    String newFolderName = custom_folder_name.getText().toString();
                    if(newFolderName.equals("")) {
                        Toast.makeText(AddImageActivity.this,"Please enter new folder name",Toast.LENGTH_SHORT).show();
                    } else {
                        DBQueries.insertFolder(AddImageActivity.this,newFolderName,COLORS[colorIndex]);
                        colorIndex=(colorIndex+1)%COLORS.length;
                        folderName = newFolderName;
                        goToNextActivity();
                    }
                }else {
                    goToNextActivity();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_DETAILS_REQUEST) {
            if (resultCode == ADD_DETAILS_RESULT_CODE) {
                this.finish();
            }
        }
    }

    private void goToNextActivity() {
        try {
            String qualityText = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
            int quality = 30;
            if (qualityText.equals("Medium"))
                quality = 60;
            else if (qualityText.equals("High"))
                quality = 80;
            else if (qualityText.equals("Custom")) {
                quality = Integer.parseInt(customQuality.getText().toString());
                if (quality < 15)
                    throw new NumberFormatException();
            }

            startActivityAccordingToFolderName(quality);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valid integer from 15 to 100",Toast.LENGTH_SHORT).show();
        }
    }

    private void startActivityAccordingToFolderName(int quality) {
        if(folderName.equals("Bills_and_Receipts")) {
            Intent intent = new Intent(AddImageActivity.this, BillsDetailsActivity.class);
            intent.putExtra("imageUri", imageUri.toString());
            intent.putExtra("imageQuality", quality);
            startActivityForResult(intent, ADD_DETAILS_REQUEST);
        } else if(folderName.equals("Medical_records")) {
            Intent intent = new Intent(AddImageActivity.this, MedicalDetailsActivity.class);
            intent.putExtra("imageUri", imageUri.toString());
            intent.putExtra("imageQuality", quality);
            startActivityForResult(intent, ADD_DETAILS_REQUEST);
        } else if(folderName.equals("Government_issued_documents")) {
            Intent intent = new Intent(AddImageActivity.this, GIDDetailsActivity.class);
            intent.putExtra("imageUri", imageUri.toString());
            intent.putExtra("imageQuality", quality);
            startActivityForResult(intent, ADD_DETAILS_REQUEST);
        } else if(folderName.equals("Certificates_and_Marksheets")) {
            Intent intent = new Intent(AddImageActivity.this, CertificateDetailsActivity.class);
            intent.putExtra("imageUri", imageUri.toString());
            intent.putExtra("imageQuality", quality);
            startActivityForResult(intent, ADD_DETAILS_REQUEST);
        } else {
            Intent intent = new Intent(AddImageActivity.this, OtherCategoryDetailsActivity.class);
            intent.putExtra("imageUri", imageUri.toString());
            intent.putExtra("folderName",folderName);
            intent.putExtra("imageQuality", quality);
            startActivityForResult(intent, ADD_DETAILS_REQUEST);
        }
    }
}

