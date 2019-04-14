package com.bellatrix.aditi.documentorganizer;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bellatrix.aditi.documentorganizer.Database.Contract;
import com.bellatrix.aditi.documentorganizer.Database.DBQueries;

public class ViewImageActivity extends AppCompatActivity implements ImageAdapter.onListItemClickLister{

    private static String folderName;
    private Cursor mCursor;

    private ImageAdapter imageAdapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        folderName = getIntent().getExtras().getString("folderName");

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  //      getSupportActionBar().setDisplayShowHomeEnabled(true);

        mCursor = DBQueries.getImageByFolder(this, folderName);

        recyclerView = (RecyclerView)findViewById(R.id.rv_images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        imageAdapter = new ImageAdapter(this, mCursor);
        recyclerView.setAdapter(imageAdapter);
    }

    @Override
    public void onListItemClick(int index) {
        // TODO: view details of image
        if (!mCursor.moveToPosition(index))
            return; // bail if returned null*/

        Intent intent = new Intent(ViewImageActivity.this, ImageDetailsActivity.class);
        intent.putExtra("folderName",folderName);
        intent.putExtra("cIndex",index);
        startActivity(intent);
    }

    // TODO: Check if memory leak for database is stopped
    @Override
    protected void onPause() {
        super.onPause();
        mCursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCursor = DBQueries.getImageByFolder(ViewImageActivity.this,folderName);
        imageAdapter.swapCursor(mCursor);
    }


}
