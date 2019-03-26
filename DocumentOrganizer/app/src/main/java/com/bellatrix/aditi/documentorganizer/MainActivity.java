package com.bellatrix.aditi.documentorganizer;

import com.bellatrix.aditi.documentorganizer.Database.Contract;
import com.bellatrix.aditi.documentorganizer.Database.DBQueries;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FolderAdapter.onListItemClickLister,
        DialogInterface.OnDismissListener{

    private Cursor folderCursor;

    private RecyclerView recyclerView;
    private FolderAdapter folderAdapter;
    FloatingActionsMenu addMenu;
    FloatingActionButton image_button, folder_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        folderCursor = DBQueries.getFolders(this);

        recyclerView = (RecyclerView) findViewById(R.id.rv_folders);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(true);
        folderAdapter = new FolderAdapter(this,folderCursor);
        recyclerView.setAdapter(folderAdapter);

        addMenu = (FloatingActionsMenu) findViewById(R.id.add_actions);
        image_button = (FloatingActionButton) findViewById(R.id.add_image_action);
        folder_button = (FloatingActionButton) findViewById(R.id.add_folder_action);

        image_button.setIcon(R.drawable.ic_image);
        folder_button.setIcon(R.drawable.ic_folder);

        image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick(v);
            }
        });
        folder_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFolderDialogFragment addFolderDialogFragment = new AddFolderDialogFragment();
                addFolderDialogFragment.setCancelable(false);
                addFolderDialogFragment.show(getFragmentManager(),"addFolder");
            }
        });
    }

    public void onSelectImageClick(View view) {
        CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(MainActivity.this, AddImageActivity.class);
                intent.putExtra("imageUri", result.getUri().toString());
                startActivity(intent);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setIconifiedByDefault(false);
//        searchManager.setOnDismissListener(new SearchManager.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                Log.e("on", "Dismissed");
//            }
//        });

        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryRefinementEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
//            case R.id.action_search:
//                super.onSearchRequested();
//                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSearchRequested() {
//        pauseSomeStuff();
//        can insert additional data here
//        Bundle appData = new Bundle();
//        appData.putBoolean(SearchableActivity.JARGON, true);
//        startSearch(null, false, appData, false);
//        return true;
        return super.onSearchRequested();
    }

    @Override
    public void onListItemClick(int index) {

        if (!folderCursor.moveToPosition(index))
            return; // bail if returned null*/

        Intent intent = new Intent(MainActivity.this, ViewImageActivity.class);
        intent.putExtra("folderName",
                folderCursor.getString(folderCursor.getColumnIndex(Contract.Folders.COLUMN_FOLDER_NAME)));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        folderCursor = DBQueries.getFolders(MainActivity.this);
        folderAdapter.swapCursor(folderCursor);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        folderCursor = DBQueries.getFolders(MainActivity.this);
        folderAdapter.swapCursor(folderCursor);
    }
}
