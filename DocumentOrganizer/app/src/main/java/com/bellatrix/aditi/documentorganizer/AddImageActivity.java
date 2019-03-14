package com.bellatrix.aditi.documentorganizer;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bellatrix.aditi.documentorganizer.Database.Contract;
import com.bellatrix.aditi.documentorganizer.Database.DBQueries;

import java.util.ArrayList;

public class AddImageActivity extends AppCompatActivity{

    private byte[] img;
    private String imageTitle, folderName;
    private ArrayList<String> folderNames;
    private int spinnerPosition;
    private Cursor folderCursor;

    private ImageView imageView;
    private TextView changeImageTitle;
    private Spinner folderSpinner;
    private Button cancelButton, nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        // TODO: Crop image

        img = getIntent().getByteArrayExtra("image");
        imageTitle="";

        imageView = (ImageView)findViewById(R.id.imagebox);
        changeImageTitle = (TextView)findViewById(R.id.tv_change_image_title);
        folderSpinner = (Spinner)findViewById(R.id.spinner_folder);
        cancelButton = (Button)findViewById(R.id.cancel_button);
        nextButton = (Button)findViewById(R.id.next_button);

        imageView.setImageBitmap(BitmapFactory.decodeByteArray(img, 0 , img.length));

        changeImageTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
                // TODO: Add custom dialogbox to add image title
            }
        });

        folderNames = new ArrayList<>();
        folderCursor = DBQueries.getFolders(this);
        folderNames.add("Select a folder");
        while (folderCursor.moveToNext()) {
            folderNames.add(folderCursor.getString(folderCursor.getColumnIndex(Contract.Folders.COLUMN_FOLDER_NAME)));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                folderNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        folderSpinner.setAdapter(adapter);
        folderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerPosition=position;
                folderName = String.valueOf(parent.getItemAtPosition(position));
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
                if(spinnerPosition==0)
                    Toast.makeText(AddImageActivity.this,"Please select a folder",Toast.LENGTH_SHORT).show();
                else {
                    DBQueries.insertDocument(AddImageActivity.this,img,imageTitle,folderName);
                    Intent intent = new Intent(AddImageActivity.this, ViewImageActivity.class);
                    intent.putExtra("folderName",folderName);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }
}

