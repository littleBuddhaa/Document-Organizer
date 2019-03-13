package com.bellatrix.aditi.documentorganizer;

import android.app.SearchManager;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


public class SearchableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        handleSearch();

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
        }
//        else if(Intent.ACTION_VIEW.equals(intent.getAction())) {
//            String selectedSuggestionRowId =  intent.getDataString();
//            //execution comes here when an item is selected from search suggestions
//            //you can continue from here with user selected search item
//            Toast.makeText(this, "selected search suggestion "+selectedSuggestionRowId,
//                    Toast.LENGTH_SHORT).show();
//        }
    }
}
