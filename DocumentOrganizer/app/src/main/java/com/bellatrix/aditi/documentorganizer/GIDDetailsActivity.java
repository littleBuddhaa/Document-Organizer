package com.bellatrix.aditi.documentorganizer;

import android.content.Intent;
import android.net.Uri;
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

import java.util.ArrayList;
import java.util.Collections;

import static com.bellatrix.aditi.documentorganizer.Utilities.Constants.GID_SUB_CATEGORIES_1;

public class GIDDetailsActivity extends AppCompatActivity {

    private static final String TAG = GIDDetailsActivity.class.getSimpleName();
    private static final int ADD_DETAILS_RESULT_CODE = 50;
    private byte[] img;
    private final String folderName = "Government issued documents";

    private EditText imageTitle, holderName;
    private LinearLayout type1;
    private Button backButton, finishButton;
    private ArrayList<CheckBox> checkBox1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gid_details);

        Uri uri = Uri.parse(getIntent().getExtras().getString("imageUri"));
        img = CommonFunctions.uriToBitmap(this,uri,TAG);

        imageTitle = (EditText)findViewById(R.id.et_image_title);
        holderName = (EditText)findViewById(R.id.et_holder_name);
        type1 = (LinearLayout) findViewById(R.id.ll_type);
        backButton = (Button)findViewById(R.id.back_button);
        finishButton = (Button)findViewById(R.id.finish_button);

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
        long id = DBQueries.insertDocument(GIDDetailsActivity.this,img,
                imageTitle.getText().toString(),folderName);

        // insertion in the table for the folder
        String val="";
        for(CheckBox checkBox: checkBox1) {
            if(checkBox.isChecked()) {
                val=val+GID_SUB_CATEGORIES_1.get(checkBox1.indexOf(checkBox))+",";
            }
        }
        if(val.length()>1)
            val = val.substring(0,val.length()-1);

        DBQueries.insertGID(GIDDetailsActivity.this,
                id,val,holderName.getText().toString());

        Intent intent = new Intent(GIDDetailsActivity.this, ViewImageActivity.class);
        intent.putExtra("folderName", folderName);
        startActivity(intent);
    }

    private void setCheckBoxes() {
        Collections.sort(GID_SUB_CATEGORIES_1);
        for(String type: GID_SUB_CATEGORIES_1) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(type);
            type1.addView(checkBox);
            checkBox1.add(checkBox);
        }
    }
}
