package com.bellatrix.aditi.documentorganizer;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
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

public class ImageDetailsActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    private static String folderName;
    private Cursor mCursor,cursor;
    private int index;
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
        folderName = getIntent().getStringExtra("folderName");

         index = getIntent().getIntExtra("cIndex",0);
        mCursor = DBQueries.getImageByFolder(ImageDetailsActivity.this, folderName);

        mCursor.moveToPosition(index);
         ImageView imgView = (ImageView) findViewById(R.id.iv);

        imgView.setImageURI(Uri.parse((mCursor.getString(mCursor.getColumnIndex(Contract.Documents.COLUMN_URI)))));
        String url = mCursor.getString(mCursor.getColumnIndex(Contract.Documents.COLUMN_TITLE));

        //sharingg
       // MenuItem item = findViewById(R.id.menu_share);






    }

    private void shareImage() {
        Intent share = new Intent(Intent.ACTION_SEND);

        // If you want to share a png image only, you can do:
        // setType("image/png"); OR for jpeg: setType("image/jpeg");
        share.setType("image/BMP");


        Uri uri = Uri.parse((mCursor.getString(mCursor.getColumnIndex(Contract.Documents.COLUMN_URI))));
        share.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(share, "Share Image!"));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.action_details:
                 linearLayout = (LinearLayout) findViewById(R.id.mainLayout);
               // linearLayout.setOrientation(LinearLayout.VERTICAL);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ImageDetailsActivity.this);
                long id = mCursor.getLong(mCursor.getColumnIndex("_ID"));
                cursor =  DBQueries.getImageById(ImageDetailsActivity.this, folderName,id);

                View mview = getLayoutInflater().inflate(R.layout.image_details_dialog,null);
                switch (folderName)
                {
                    case "Bills & Receipts":

                        for( int i = 1; i < 7; i++ )
                        {
                            TextView textView = new TextView(this);
                            String str1 = cursor.getString(i);
                            if(!str1.equals("")) {
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
                            if(!str1.equals("")) {
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
                            if(!str1.equals("")) {
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
                            if(!str1.equals("")) {
                                textView.setText(cursor.getColumnName(i) + "  :  " + str1);
                                linearLayout.addView(textView);
                            }

                        }
                        break;
                    case "Certificates & Marksheets":
                        for( int i = 3; i < 7; i++ )
                        {
                            TextView textView = new TextView(this);
                            String str1 = cursor.getString(i);
                            if(!str1.equals("")) {
                                textView.setText(cursor.getColumnName(i) + "  :  " + str1);
                                linearLayout.addView(textView);
                            }

                        }
                        break;
                    default:
                        TextView textView = new TextView(this);
                        String str1 = cursor.getString(cursor.getColumnIndex("CustomTags"));
                        if(!str1.equals("")) {
                            textView.setText(cursor.getColumnName(cursor.getColumnIndex("CustomTags")) + "  :  " + str1);
                            linearLayout.addView(textView);
                        }

                }
                mBuilder.setView(mview);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                return true;
            case R.id.menu_share:
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                View.OnClickListener handler = new View.OnClickListener() {
                    public void onClick(View v) {
                            shareImage();
                        }

                };

                findViewById(R.id.menu_share).setOnClickListener(handler);
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

