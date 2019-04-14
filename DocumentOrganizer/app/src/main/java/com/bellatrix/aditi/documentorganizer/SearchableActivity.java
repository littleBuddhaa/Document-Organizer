package com.bellatrix.aditi.documentorganizer;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bellatrix.aditi.documentorganizer.Database.Contract;
import com.bellatrix.aditi.documentorganizer.Database.DBQueries;
import com.bellatrix.aditi.documentorganizer.Utilities.SearchSuggestionProvider;


public class SearchableActivity extends AppCompatActivity implements ImageSearchAdapter.onListItemClickLister{

    private String query;
    private Cursor mCursor;

    private RecyclerView recyclerView;
    ImageSearchAdapter imageSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        recyclerView = (RecyclerView)findViewById(R.id.rv_search_images);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        handleSearch();
        mCursor = DBQueries.searchImage(this,query);

        imageSearchAdapter = new ImageSearchAdapter(this, mCursor);
        recyclerView.setAdapter(imageSearchAdapter);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleSearch();
    }

    private void handleSearch() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            Toast.makeText(this,query,Toast.LENGTH_SHORT).show();
            query = "";
        }
//        else if(Intent.ACTION_VIEW.equals(intent.getAction())) {
//            String selectedSuggestionRowId =  intent.getDataString();
//            //execution comes here when an item is selected from search suggestions
//            //you can continue from here with user selected search item
//            Toast.makeText(this, "selected search suggestion "+selectedSuggestionRowId,
//                    Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onListItemClick(int index) {
        if (!mCursor.moveToPosition(index))
            return; // bail if returned null*/

        Intent intent = new Intent(SearchableActivity.this, ImageDetailsActivity.class);
        intent.putExtra("folderName",mCursor.getString(mCursor.getColumnIndex(Contract.Documents.COLUMN_CATEGORY)));
        intent.putExtra("cIndex",index);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCursor = DBQueries.searchImage(SearchableActivity.this,query);
        imageSearchAdapter.swapCursor(mCursor);
    }
}
