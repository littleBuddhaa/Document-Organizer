package com.bellatrix.aditi.documentorganizer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bellatrix.aditi.documentorganizer.Database.Contract;
import com.bellatrix.aditi.documentorganizer.Database.DBQueries;
import com.bellatrix.aditi.documentorganizer.Utilities.CommonFunctions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ImageDetailsActivity extends AppCompatActivity {

//    private byte[] image;
    private int index;
    private long id;
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
        //Toolbar toolbar  = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setBackgroundColor(0);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }

        //TextView dtitle = (TextView)findViewById(R.id.dialogtitle);
        //dtitle.setText("Image Tags");
        //toolbar.setTitle("Image Tags");


        folderName = getIntent().getExtras().getString("folderName");
        index = getIntent().getIntExtra("cIndex",0);
        id = getIntent().getLongExtra("id",1);

        mCursor = DBQueries.getImageById(ImageDetailsActivity.this, Contract.Documents.TABLE_NAME,id);

        String title = "Untitled Document";
        String res = mCursor.getString(mCursor.getColumnIndex(Contract.Documents.COLUMN_TITLE));
        if(res!=null)
            title = res;
        setTitle(CommonFunctions.toReadableString(title));
        ImageView imgView = (ImageView) findViewById(R.id.iv);

        byte[] image = mCursor.getBlob(mCursor.getColumnIndex(Contract.Documents.COLUMN_IMAGE));
        bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);

        imgView.setImageBitmap(bmp);
        String url = mCursor.getString(mCursor.getColumnIndex(Contract.Documents.COLUMN_TITLE));
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

    public TextView getImageDetailsTextView(String textInTextView) {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,10);
        textView.setLayoutParams(params);
        textView.setTextSize(16);
        textView.setTextColor(getResources().getColor(R.color.dark_gray));
        textView.setBackgroundColor(getResources().getColor(R.color.transparent));
        textView.setPadding(15, 15, 15, 15);
        textView.setText(Html.fromHtml(textInTextView) );
        return textView;
    }

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
                    case "Bills_and_Receipts":

                        for( int i = 1; i < 7; i++ )
                        {
                            String str1 = cursor.getString(i);
                            if(str1!=null) {
                                String out = "<b>" + CommonFunctions.splitCamelCase(cursor.getColumnName(i)) +"  :  "+"</b>"+str1 + "   ";
                                TextView textView = getImageDetailsTextView(out);
                                linearLayout.addView(textView);
                            }

                        }
                        break;
                    case "Medical_records":
                        Log.d("myTags","hello");
                        for( int i = 1; i < 6; i++ )
                        {
                            String str1 = cursor.getString(i);
                            if(str1!=null) {
                                String out = "<b>" + CommonFunctions.splitCamelCase(cursor.getColumnName(i)) +"  :  "+"</b>"+str1 + "   ";
                                TextView textView = getImageDetailsTextView(out);
                                linearLayout.addView(textView);
                            }

                        }
                        break;
                    case "Government_issued_documents":
                        for( int i = 1; i < 4; i++ )
                        {
                            String str1 = cursor.getString(i);
                            if(str1!=null) {
                                String out = "<b>" + CommonFunctions.splitCamelCase(cursor.getColumnName(i)) +"  :  "+"</b>"+str1 + "   ";                                TextView textView = getImageDetailsTextView(out);
                                linearLayout.addView(textView);
                            }
                        }
                        break;
                    case "Handwritten":
                        for( int i = 1; i < 2; i++ )
                        {
                            String str1 = cursor.getString(i);
                            if(str1!=null) {
                                String out = "<b>" + CommonFunctions.splitCamelCase(cursor.getColumnName(i)) +"  :  "+"</b>"+str1 + "   ";                                TextView textView = getImageDetailsTextView(out);
                                linearLayout.addView(textView);
                            }
                        }
                        break;
                    case "Certificates_and_Marksheets":
                        for( int i = 3; i < 7; i++ )
                        {
                            String str1 = cursor.getString(i);
                            if(str1!=null) {
                                String out = "<b>" + CommonFunctions.splitCamelCase(cursor.getColumnName(i)) +"  :  "+"</b>"+str1 + "   ";                                TextView textView = getImageDetailsTextView(out);
                                linearLayout.addView(textView);
                            }

                        }
                        break;
                    default:
                        String str1 = cursor.getString(cursor.getColumnIndex("CustomTags"));
                        if(str1!=null) {
                            String out =  "<b>" + CommonFunctions.splitCamelCase(cursor.getColumnName(cursor.getColumnIndex("CustomTags"))) + "  :  " + "</b>"+ str1 + "   ";
                            TextView textView = getImageDetailsTextView(out);
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

