package com.bellatrix.aditi.documentorganizer;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.bellatrix.aditi.documentorganizer.Database.Contract;
import com.bellatrix.aditi.documentorganizer.Database.DBQueries;
import com.bellatrix.aditi.documentorganizer.Utilities.CommonFunctions;

public class ViewImageActivity extends AppCompatActivity implements ImageAdapter.onListItemClickLister{

    private static String folderName;
    private String sortBy;
    private Cursor mCursor;

    private ImageAdapter imageAdapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        folderName = getIntent().getExtras().getString("folderName");
        setTitle(CommonFunctions.toReadableString(folderName));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  //      getSupportActionBar().setDisplayShowHomeEnabled(true);

        sortBy = " DESC";
        mCursor = DBQueries.getImageByFolder(this, folderName,sortBy);

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
        long id = mCursor.getLong(mCursor.getColumnIndex(Contract.Documents.COLUMN_ID));

        intent.putExtra("id",id);

        intent.putExtra("folderName",folderName);
        intent.putExtra("cIndex",index);
        startActivity(intent);
    }


    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_gallery, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_folder).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        Bundle bundle = new Bundle();
        bundle.putString("folderName", folderName);
        searchView.setAppSearchData(bundle);

        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryRefinementEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
//            case R.id.action_search_folder:
//                super.onSearchRequested();
//                return true;
            case R.id.action_sort:
                if(sortBy.equals(" DESC"))
                    sortBy = " ASC";
                else sortBy = " DESC";
                mCursor = DBQueries.getImageByFolder(ViewImageActivity.this,folderName,sortBy);
                imageAdapter.swapCursor(mCursor);
                return true;
            case R.id.action_filter:
                return true;
            case R.id.action_rename_folder:
                return true;
            case R.id.action_delete_folder:
                delete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void delete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Delete "+CommonFunctions.toReadableString(folderName)+"?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                DBQueries.deleteFolder(ViewImageActivity.this,folderName);
                                finish();
                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
        mCursor = DBQueries.getImageByFolder(ViewImageActivity.this,folderName,sortBy);
        imageAdapter.swapCursor(mCursor);
    }


}
