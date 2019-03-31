package com.bellatrix.aditi.documentorganizer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.bellatrix.aditi.documentorganizer.Database.DBQueries;
import com.bellatrix.aditi.documentorganizer.Utilities.CommonFunctions;

public class OtherCategoryDetailsActivity extends AppCompatActivity {

    private static final String TAG = OtherCategoryDetailsActivity.class.getSimpleName();
    private static final int ADD_DETAILS_RESULT_CODE = 50;
    private byte[] img;
    private String folderName;

    private EditText imageTitle;
    private Button backButton, finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_category_details);

        Uri uri = Uri.parse(getIntent().getExtras().getString("imageUri"));
        img = CommonFunctions.uriToBitmap(this,uri,TAG);
        folderName = getIntent().getExtras().getString("folderName");

        imageTitle = (EditText)findViewById(R.id.et_image_title);
        backButton = (Button)findViewById(R.id.back_button);
        finishButton = (Button)findViewById(R.id.finish_button);

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
        long id = DBQueries.insertDocument(OtherCategoryDetailsActivity.this,img,
                imageTitle.getText().toString(),folderName);

        DBQueries.insertIntoFolder(OtherCategoryDetailsActivity.this,
                folderName,id,"");

        Intent intent = new Intent(OtherCategoryDetailsActivity.this, ViewImageActivity.class);
        intent.putExtra("folderName", folderName);
        startActivity(intent);
    }
}
