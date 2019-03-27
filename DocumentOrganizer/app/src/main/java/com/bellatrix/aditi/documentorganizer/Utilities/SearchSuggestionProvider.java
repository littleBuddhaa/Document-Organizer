package com.bellatrix.aditi.documentorganizer.Utilities;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Aditi on 13-03-2019.
 */
public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.bellatrix.aditi.SearchSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}