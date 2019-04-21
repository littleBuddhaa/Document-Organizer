package com.bellatrix.aditi.documentorganizer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bellatrix.aditi.documentorganizer.Database.Contract;
import com.bellatrix.aditi.documentorganizer.Database.DBQueries;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ImageDetailsActivity extends AppCompatActivity {

//    private byte[] image;
    private int index;
    private Bitmap bmp;

    private LinearLayout linearLayout;
    private static String folderName;
    private Cursor mCursor,cursor;

    // Keep reference to the ShareActionProvider from the menu
    private ShareActionProvider shareActionProvider;
    private ShareActionProvider miShareAction;
    private Intent shareIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        //ImageView ImageView = (ImageView) findViewById(R.id.imageView);
     //   Uri uri =  Uri.parse(getIntent().getExtras().getString("imageUri"));
       // ImageView.setImageURI(uri);
        folderName = getIntent().getExtras().getString("folderName");

         index = getIntent().getIntExtra("cIndex",0);
        mCursor = DBQueries.getImageByFolder(ImageDetailsActivity.this, folderName);

        mCursor.moveToPosition(index);
         ImageView imgView = (ImageView) findViewById(R.id.iv);

        byte[] image = mCursor.getBlob(mCursor.getColumnIndex(Contract.Documents.COLUMN_IMAGE));
        bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);

        imgView.setImageBitmap(bmp);
        String url = mCursor.getString(mCursor.getColumnIndex(Contract.Documents.COLUMN_TITLE));

        //sharingg
       // MenuItem item = findViewById(R.id.menu_share);





    }

    private void shareMyImage(){

       String name = mCursor.getString(mCursor.getColumnIndex(Contract.Documents.COLUMN_TITLE));


        try {

            File cachePath = new File(getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            //FileOutputStream stream = new FileOutputStream(cachePath + "/" + name  + ".png"); // overwrites this image every time
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png");
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        File imagePath = new File(getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(ImageDetailsActivity.this, "com.bellatrix.aditi.fileprovider", newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }



    }




//    private void shareImage() {
//        Intent share = new Intent(Intent.ACTION_SEND);
//
//        // If you want to share a png image only, you can do:
//        // setType("image/png"); OR for jpeg: setType("image/jpeg");
//        share.setType("image/*");
//        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//        Uri uri = Uri.parse((mCursor.getString(mCursor.getColumnIndex(Contract.Documents.COLUMN_URI))));
//        share.putExtra(Intent.EXTRA_STREAM, uri);
//
//        startActivity(Intent.createChooser(share, "Share Image!"));
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.action_details:


             //   linearLayout.setOrientation(LinearLayout.VERTICAL);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ImageDetailsActivity.this);
                long id = mCursor.getLong(mCursor.getColumnIndex(Contract.Documents.COLUMN_ID));

                cursor =  DBQueries.getImageById(ImageDetailsActivity.this, folderName,id);

                View mview = getLayoutInflater().inflate(R.layout.image_details_dialog,null);

                linearLayout = (LinearLayout) mview.findViewById(R.id.mainLayout);
                switch (folderName)
                {
                    case "Bills & Receipts":

                        for( int i = 1; i < 7; i++ )
                        {
                            TextView textView = new TextView(this);
                            String str1 = cursor.getString(i);

                            if(str1!=null) {
                                textView.setText(cursor.getColumnName(i) + "  :  " + str1);
                                linearLayout.addView(textView);
                            }

                        }
                        break;
                    case "Medical records":
                        for( int i = 1; i < 6; i++ )
                        {
                            TextView textView = new TextView(this);
                            String str1 = cursor.getString(i);
                            if(str1!=null) {
                                textView.setText(cursor.getColumnName(i) + "  :  " + str1);
                                linearLayout.addView(textView);
                            }

                        }
                        break;
                    case "Government issued documents":
                        for( int i = 1; i < 4; i++ )
                        {
                            TextView textView = new TextView(this);
                            String str1 = cursor.getString(i);
                            if(str1!=null) {
                                textView.setText(cursor.getColumnName(i) + "  :  " + str1);
                                linearLayout.addView(textView);
                            }

                        }
                        break;
                    case "Handwritten":
                        for( int i = 1; i < 2; i++ )
                        {
                            TextView textView = new TextView(this);
                            String str1 = cursor.getString(i);
                            if(str1!=null) {
                                textView.setText(cursor.getColumnName(i) + "  :  " + str1);
                                linearLayout.addView(textView);
                            }

                        }
                        break;
                    case "Certificates":
                        for( int i = 3; i < 7; i++ )
                        {
                            TextView textView = new TextView(this);
                            String str1 = cursor.getString(i);
                            if(str1!=null) {
                                textView.setText(cursor.getColumnName(i) + "  :  " + str1);
                                linearLayout.addView(textView);
                            }

                        }
                        break;
                    default:
                        TextView textView = new TextView(this);
                        String str1 = cursor.getString(cursor.getColumnIndex("CustomTags"));
                        if(str1!=null) {
                            textView.setText(cursor.getColumnName(cursor.getColumnIndex("CustomTags")) + "  :  " + str1);
                            linearLayout.addView(textView);
                        }

                }
                mBuilder.setView(mview);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                return true;
           case R.id.action_share:
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                View.OnClickListener handler = new View.OnClickListener() {
                    public void onClick(View v) {
                            //Context context = getContext();

                        shareMyImage();
                        }

                };

                findViewById(R.id.action_share).setOnClickListener(handler);
               return true;


        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }


}

