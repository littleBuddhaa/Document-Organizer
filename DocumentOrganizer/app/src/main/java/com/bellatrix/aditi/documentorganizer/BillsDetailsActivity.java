package com.bellatrix.aditi.documentorganizer;

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
import com.bellatrix.aditi.documentorganizer.Utilities.DateUtil;

import java.util.Collections;

import static com.bellatrix.aditi.documentorganizer.Utilities.Constants.BNR_SUB_CATEGORIES_1;
import static com.bellatrix.aditi.documentorganizer.Utilities.Constants.BNR_SUB_CATEGORIES_2;

public class BillsDetailsActivity extends AppCompatActivity {

    private EditText purchaseDate, imageTitle;
    private ImageButton datePicker;
    private LinearLayout productType, productName;
    private Button backButton, finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_details);

        purchaseDate = (EditText)findViewById(R.id.et_purchase_date);
        datePicker = (ImageButton)findViewById(R.id.date_picker_button);
        productName = (LinearLayout) findViewById(R.id.ll_product_name);
        productType = (LinearLayout) findViewById(R.id.ll_product_type);
        imageTitle = (EditText)findViewById(R.id.et_image_title);
        backButton = (Button)findViewById(R.id.back_button);
        finishButton = (Button)findViewById(R.id.finish_button);

        setCheckBoxes();

        String title = "Bill & Receipts_"
                +String.valueOf(DBQueries.getTotalImageByFolder(this, "Bill & Receipts")+1);
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
                // TODO: Implement the functionality
            }
        });
    }

    private void setCheckBoxes() {
        Collections.sort(BNR_SUB_CATEGORIES_1);
        Collections.sort(BNR_SUB_CATEGORIES_2);
        for(String type: BNR_SUB_CATEGORIES_1) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(type);
            productType.addView(checkBox);
        }
        for(String name: BNR_SUB_CATEGORIES_2) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(name);
            productName.addView(checkBox);
        }
    }

    public void setDate(DateUtil date) {
        String day = String.valueOf(date.getDay());
        String month = String.valueOf(date.getMonth());
        String year = String.valueOf(date.getYear());
        if(day.length()==1) day="0"+day;
        if(month.length()==1) month="0"+month;
        purchaseDate.setText(day+"-"+month+"-"+year);
    }

}
