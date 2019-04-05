package com.bellatrix.aditi.documentorganizer;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;

import com.bellatrix.aditi.documentorganizer.Utilities.MyCustomPagerAdapter;

public class ImageDetailsActivity extends AppCompatActivity {

    private static String folderName;
    private Cursor mCursor;
    private int index;
    // Keep reference to the ShareActionProvider from the menu
    private ShareActionProvider mShareActionProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        //ImageView ImageView = (ImageView) findViewById(R.id.imageView);
     //   Uri uri =  Uri.parse(getIntent().getExtras().getString("imageUri"));
       // ImageView.setImageURI(uri);
        folderName = getIntent().getStringExtra("folderName");

         index = getIntent().getIntExtra("cIndex",0);
        ViewPager viewPager = findViewById(R.id.view_pager);
        MyCustomPagerAdapter adapter = new MyCustomPagerAdapter(this, folderName,index);
        viewPager.setAdapter(adapter);
    }

}