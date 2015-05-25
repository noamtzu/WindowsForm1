package com.shoppee.shoppeeshell.SearchActivity;

import android.app.SearchManager;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by noam.tzuberi on 25/05/2015.
 */
public class SearchProductsSuggestionProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = "com.shoppee.shoppeeshell.SearchActivity.SearchProductsSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES | DATABASE_MODE_2LINES;

    public SearchProductsSuggestionProvider() {

        setupSuggestions(AUTHORITY, MODE);
    }

    public Cursor query(Uri uri, String[] projection, String sel, String[] selArgs, String sortOrder) {

        MatrixCursor customCursor = new MatrixCursor(new String[] {
                BaseColumns._ID,
                SearchManager.SUGGEST_COLUMN_TEXT_1,
                SearchManager.SUGGEST_COLUMN_TEXT_2,
                SearchManager.SUGGEST_COLUMN_ICON_1
                /*SearchManager.SUGGEST_COLUMN_INTENT_ACTION*/
        });

        customCursor.addRow(new Object[] { 0, "Plants", "Search Plants", android.R.drawable.ic_menu_camera/*, Search.SEARCH_PLANTS_ACTION*/});
        customCursor.addRow(new Object[] { 1, "Birds", "Search Birds", android.R.drawable.ic_menu_search/*, Search.SEARCH_BIRDS_ACTION*/ });

        //Cursor recentCursor = super.query(uri, projection, sel,selArgs, sortOrder);
        Cursor[] cursors = new Cursor[] { /*recentCursor,*/ customCursor};

        return new MergeCursor( cursors );

        //return recentCursor;
    }
}
