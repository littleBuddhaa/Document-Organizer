package com.bellatrix.aditi.documentorganizer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bellatrix.aditi.documentorganizer.Database.DBQueries;
import com.bellatrix.aditi.documentorganizer.Utilities.CommonFunctions;

import static java.sql.Types.NULL;

public class OtherCategoryDetailsActivity extends AppCompatActivity {

    private static final String TAG = OtherCategoryDetailsActivity.class.getSimpleName();
    private static final int ADD_DETAILS_RESULT_CODE = 50;
    private byte[] img;
    private String folderName;
    String textRecognized;

    private static EditText imageTitle,customTags;
    private Button backButton, finishButton,addCustomTags;
    private  Uri uri;
    static String  ctag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_category_details);

        uri = Uri.parse(getIntent().getExtras().getString("imageUri"));
        textRecognized = CommonFunctions.getTextFromUri(this, uri, TAG);
        int quality = getIntent().getExtras().getInt("imageQuality");
        img = CommonFunctions.uriToBytes(this,uri,TAG,quality);
        folderName = getIntent().getExtras().getString("folderName");

        imageTitle = (EditText)findViewById(R.id.et_image_title);
        backButton = (Button)findViewById(R.id.back_button);
        finishButton = (Button)findViewById(R.id.finish_button);
        addCustomTags = (Button) findViewById(R.id.btn_custom_tags);
        customTags = (EditText)findViewById(R.id.et_custom_tags);
        String title = folderName+"_"
                +String.valueOf(DBQueries.getTotalImageByFolder(this, folderName)+1);
        imageTitle.setText(title);
        addCustomTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherCategoryDetailsActivity.this, SelectCustomTags.class);
                intent.putExtra("textRecognized", textRecognized);
                intent.putExtra("classname","OtherCategoryDetailsActivity");
                String out = customTags.getText().toString();
                if(out==null)
                    out="";

                intent.putExtra("initialvalue",out);
                startActivity(intent);


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
                Intent intent = new Intent(OtherCategoryDetailsActivity.this, ViewImageActivity.class);
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
    private void handleData() {

        // insertion in global table
        long id = DBQueries.insertDocument(OtherCategoryDetailsActivity.this,NULL,img,
                imageTitle.getText().toString(),folderName);

        DBQueries.insertIntoFolder(OtherCategoryDetailsActivity.this,
                folderName,id,customTags.getText().toString());
    }
}
