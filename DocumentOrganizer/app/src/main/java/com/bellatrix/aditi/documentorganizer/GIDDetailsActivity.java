package com.bellatrix.aditi.documentorganizer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bellatrix.aditi.documentorganizer.Database.DBQueries;
import com.bellatrix.aditi.documentorganizer.Utilities.CommonFunctions;

import java.util.Collections;

import static com.bellatrix.aditi.documentorganizer.Utilities.Constants.GID_SUB_CATEGORIES_1;

public class GIDDetailsActivity extends AppCompatActivity {

    private static final String TAG = GIDDetailsActivity.class.getSimpleName();
    private static final int ADD_DETAILS_RESULT_CODE = 50;
    private byte[] img;
    private final String folderName = "Government issued documents";

    private EditText imageTitle, holderName;
    private RadioGroup radioGroup;
    private Button backButton, finishButton;
    private  Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gid_details);

         uri = Uri.parse(getIntent().getExtras().getString("imageUri"));
        int quality = getIntent().getExtras().getInt("imageQuality");
        img = CommonFunctions.uriToBytes(this,uri,TAG,quality);

        imageTitle = (EditText)findViewById(R.id.et_image_title);
        holderName = (EditText)findViewById(R.id.et_holder_name);
        radioGroup = (RadioGroup)findViewById(R.id.radio_grp);
        backButton = (Button)findViewById(R.id.back_button);
        finishButton = (Button)findViewById(R.id.finish_button);

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
        long id = DBQueries.insertDocument(GIDDetailsActivity.this,img,
                imageTitle.getText().toString(),folderName,uri.toString());

        // insertion in the table for the folder
        String val=((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();;

        DBQueries.insertGID(GIDDetailsActivity.this,
                id,val,holderName.getText().toString());

        Intent intent = new Intent(GIDDetailsActivity.this, ViewImageActivity.class);
        intent.putExtra("folderName", folderName);
        startActivity(intent);
    }

    private void setRadioGroup() {
        Collections.sort(GID_SUB_CATEGORIES_1);
        for(String type: GID_SUB_CATEGORIES_1) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(type);
            radioGroup.addView(radioButton);
        }
    }
}
