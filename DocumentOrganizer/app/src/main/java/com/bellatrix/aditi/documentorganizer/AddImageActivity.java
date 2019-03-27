package com.bellatrix.aditi.documentorganizer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bellatrix.aditi.documentorganizer.Database.Contract;
import com.bellatrix.aditi.documentorganizer.Database.DBQueries;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.bellatrix.aditi.documentorganizer.Utilities.Constants.COLORS;
import static com.bellatrix.aditi.documentorganizer.Utilities.Constants.colorIndex;

public class AddImageActivity extends AppCompatActivity{

    private static final String TAG = AddImageActivity.class.getSimpleName();
    private byte[] img;
    private String folderName;
    private ArrayList<String> folderNames;
    private int spinnerPosition;
    private Cursor folderCursor;

    private ImageView imageView;
    private EditText custom_folder_name;
    private Spinner folderSpinner;
    private Button cancelButton, nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        Uri uri = Uri.parse(getIntent().getExtras().getString("imageUri"));
        uriToBitmap(uri);

        imageView = (ImageView)findViewById(R.id.imagebox);
        custom_folder_name = (EditText)findViewById(R.id.et_folder_name);
        folderSpinner = (Spinner)findViewById(R.id.spinner_folder);
        cancelButton = (Button)findViewById(R.id.cancel_button);
        nextButton = (Button)findViewById(R.id.next_button);

        imageView.setImageBitmap(BitmapFactory.decodeByteArray(img, 0 , img.length));

        folderNames = new ArrayList<String>();
        folderCursor = DBQueries.getFolders(this);
        folderNames.add("Select a folder");

        while (folderCursor.moveToNext()) {
            folderNames.add(folderCursor.getString(folderCursor.getColumnIndex(Contract.Folders.COLUMN_FOLDER_NAME)));
        }
        folderNames.add("Add custom folder");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                folderNames){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                    return false;
                else
                    return true;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
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
                if(spinnerPosition==0)
                    Toast.makeText(AddImageActivity.this,"Please select a folder",Toast.LENGTH_SHORT).show();
                else if(spinnerPosition==(folderNames.size()-1)){
                    String newFolderName = custom_folder_name.getText().toString();
                    if(newFolderName==null) {
                        Toast.makeText(AddImageActivity.this,"Please enter new folder name",Toast.LENGTH_SHORT).show();
                    } else {
                        DBQueries.insertFolder(AddImageActivity.this,newFolderName,COLORS[colorIndex]);
                        colorIndex++;
                        folderName = newFolderName;
                        startActivityAccordingToFolderName();
                    }
                }else {
                    startActivityAccordingToFolderName();
                }
            }
        });
    }

    private void startActivityAccordingToFolderName() {
//        DBQueries.insertDocument(AddImageActivity.this,img,imageTitle,folderName);
        Intent intent = new Intent(AddImageActivity.this, AddImageDetailsActivity.class);
        intent.putExtra("folderName",folderName);
        startActivity(intent);
    }

    private void uriToBitmap(Uri uri) {
        if (uri != null) {
            try {
                Bitmap photo = (Bitmap) MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                upload(photo);

            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
                Toast.makeText(this, "Image picking failed", Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
            Toast.makeText(this, "Image picker gave us a null image.", Toast.LENGTH_LONG).show();
        }
    }

    private void upload(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        img = stream.toByteArray();
    }
}

