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

import static com.bellatrix.aditi.documentorganizer.Utilities.Constants.BNR_SUB_CATEGORIES_1;
import static com.bellatrix.aditi.documentorganizer.Utilities.Constants.BNR_SUB_CATEGORIES_2;
import static java.sql.Types.NULL;

public class BillsDetailsActivity extends AppCompatActivity {

    private static final String TAG = AddImageActivity.class.getSimpleName();
    private static final int ADD_DETAILS_RESULT_CODE = 50;

    private byte[] img;
    private final String folderName = "BNR";

    private EditText purchaseDate, imageTitle, total, enterprise;
    private ImageButton datePicker;
    private LinearLayout productType, productName;
    private Button backButton, finishButton;
    private ArrayList<CheckBox> checkBox1, checkBox2;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_details);

        uri = Uri.parse(getIntent().getExtras().getString("imageUri"));
        int quality = getIntent().getExtras().getInt("imageQuality");
        img = CommonFunctions.uriToBytes(this,uri,TAG,quality);

        purchaseDate = (EditText)findViewById(R.id.et_purchase_date);
        imageTitle = (EditText)findViewById(R.id.et_image_title);
        total = (EditText)findViewById(R.id.et_total);
        enterprise = (EditText)findViewById(R.id.et_enterprise);
        datePicker = (ImageButton)findViewById(R.id.date_picker_button);
        productName = (LinearLayout) findViewById(R.id.ll_product_name);
        productType = (LinearLayout) findViewById(R.id.ll_product_type);
        backButton = (Button)findViewById(R.id.back_button);
        finishButton = (Button)findViewById(R.id.finish_button);

        checkBox1 = new ArrayList<>();
        checkBox2 = new ArrayList<>();
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
                    newFragment.setClass(BillsDetailsActivity.this);
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
                Intent intent = new Intent(BillsDetailsActivity.this, ViewImageActivity.class);
                intent.putExtra("folderName", folderName);
                startActivity(intent);
                finish();
            }
        });
    }

    private void handleData() {

        // insertion in global table
        long id=DBQueries.insertDocument(this,NULL,img,
                imageTitle.getText().toString(),folderName,uri.toString());
        //long id = DBQueries.getLastId(this);
        // insertion in the table for the folder
        String val1="", val2="";
        for(CheckBox checkBox: checkBox1) {
            if(checkBox.isChecked()) {
                val1=val1+BNR_SUB_CATEGORIES_1.get(checkBox1.indexOf(checkBox))+",";
            }
        }
        for(CheckBox checkBox: checkBox2) {
            if(checkBox.isChecked()) {
                val2=val2+BNR_SUB_CATEGORIES_2.get(checkBox2.indexOf(checkBox))+",";
            }
        }
        if(val1.length()>1)
            val1 = val1.substring(0,val1.length()-1);
        if(val2.length()>1)
            val2 = val2.substring(0,val2.length()-1);

        DBQueries.insertBNR(this,
                id,purchaseDate.getText().toString(),
                val1,val2,
                total.getText().toString(),enterprise.getText().toString());
    }

    private void setCheckBoxes() {
        Collections.sort(BNR_SUB_CATEGORIES_1);
        Collections.sort(BNR_SUB_CATEGORIES_2);
        for(String type: BNR_SUB_CATEGORIES_1) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(type);
            productType.addView(checkBox);
            checkBox1.add(checkBox);
        }
        for(String name: BNR_SUB_CATEGORIES_2) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(name);
            productName.addView(checkBox);
            checkBox2.add(checkBox);
        }
    }

    public void setDate(DateUtil date) {
        String day = String.valueOf(date.getDay());
        String month = String.valueOf(date.getMonth());
        String year = String.valueOf(date.getYear());
        if(day.length()==1) day="0"+day;
        if(month.length()==1) month="0"+month;
        purchaseDate.setText(year+"-"+month+"-"+day);
    }
}
