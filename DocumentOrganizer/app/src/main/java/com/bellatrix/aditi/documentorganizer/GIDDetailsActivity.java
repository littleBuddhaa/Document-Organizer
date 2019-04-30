package com.bellatrix.aditi.documentorganizer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bellatrix.aditi.documentorganizer.Database.DBQueries;
import com.bellatrix.aditi.documentorganizer.Utilities.CommonFunctions;
import com.bellatrix.aditi.documentorganizer.Utilities.DialogProductType;

import java.util.Collections;

import static com.bellatrix.aditi.documentorganizer.Utilities.Constants.BNR_SUB_CATEGORIES_1;
import static com.bellatrix.aditi.documentorganizer.Utilities.Constants.GID_SUB_CATEGORIES_1;
import static java.sql.Types.NULL;

public class GIDDetailsActivity extends AppCompatActivity implements DialogProductType.DialogListenerPType {

    private static final String TAG = GIDDetailsActivity.class.getSimpleName();
    private static final int ADD_DETAILS_RESULT_CODE = 50;
    private byte[] img;
    private final String folderName = "Government_issued_documents";

    private static EditText imageTitle, holderName,customTags;
    private RadioGroup radioGroup;

    private Button backButton, finishButton,addCustomTags;
    private ImageButton addType;

    private  Uri uri;
    static String  ctag;
    String textRecognized;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gid_details);

         uri = Uri.parse(getIntent().getExtras().getString("imageUri"));
        int quality = getIntent().getExtras().getInt("imageQuality");
        img = CommonFunctions.uriToBytes(this,uri,TAG,quality);

        addType = (ImageButton) findViewById(R.id.btn_type);

        textRecognized = CommonFunctions.getTextFromUri(this, uri, TAG);

        imageTitle = (EditText)findViewById(R.id.et_image_title);
        holderName = (EditText)findViewById(R.id.et_holder_name);
        radioGroup = (RadioGroup)findViewById(R.id.radio_grp);
        addCustomTags = (Button) findViewById(R.id.btn_custom_tags);
        customTags = (EditText)findViewById(R.id.et_custom_tags);
        addCustomTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GIDDetailsActivity.this, SelectCustomTags.class);
                intent.putExtra("textRecognized", textRecognized);
                String out = customTags.getText().toString();
                if(out==null)
                    out="";

                intent.putExtra("initialvalue",out);
                intent.putExtra("classname","GIDDetailsActivity");
                startActivity(intent);


            }
        });

        backButton = (Button)findViewById(R.id.back_button);
        finishButton = (Button)findViewById(R.id.finish_button);

        setRadioGroup();

        String title = folderName+"_"
                +String.valueOf(DBQueries.getTotalImageByFolder(this, folderName)+1);
        imageTitle.setText(title);

        addType.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(GIDDetailsActivity.this, ViewImageActivity.class);
                intent.putExtra("folderName", folderName);
                startActivity(intent);
                finish();
            }
        });
    }
    public static void setter(String s)
    {
        ctag = s;
        Log.d("mytag",s);
        customTags.setText(ctag);
    }


    public void openDialogReportType()
    {
        DialogProductType dpt = new DialogProductType();
        dpt.show(getSupportFragmentManager(), "Adding GID Type");
    }



    private void handleData() {

        // insertion in global table
        long id =  DBQueries.insertDocument(GIDDetailsActivity.this,NULL,img,
                imageTitle.getText().toString(),folderName);

        // insertion in the table for the folder
        String val="";
        RadioButton button;
        if((button=(RadioButton)findViewById(radioGroup.getCheckedRadioButtonId()))!=null)
            val = button.getText().toString();

        DBQueries.insertGID(GIDDetailsActivity.this,
                id,val,holderName.getText().toString(),customTags.getText().toString());
    }

    private void setRadioGroup() {
        Collections.sort(GID_SUB_CATEGORIES_1);
        for(String type: GID_SUB_CATEGORIES_1) {
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


    @Override
    public void applyTexts1(String ptype) {
        GID_SUB_CATEGORIES_1.add(ptype);
        setRadioGroup1(ptype);
    }
}
