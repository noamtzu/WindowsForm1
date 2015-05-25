package com.shoppee.shoppeeshell;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    SearchManager searchManager;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle(R.string.app_header_name);
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {

            return false;
        }

        return true;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        //searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(getApplicationContext(), MainActivity.class)));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        SearchView.SearchAutoComplete autoCompleteTextView = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);

        if(autoCompleteTextView != null)
        {
            autoCompleteTextView.setDropDownBackgroundResource(R.drawable.abc_dialog_material_background_dark);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId())
        {
            case R.id.action_camera:
                ScanBarcode();
                return true;

            case R.id.action_voice_search_product:
                SpeechToText();
                return true;
        }

        return false;
    }

    private void ScanBarcode()
    {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
        startActivityForResult(intent, 1);
    }

    private void SpeechToText()
    {
        try {
            Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            startActivityForResult(i, 10);
        }
        catch(ActivityNotFoundException e)
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,   Uri.parse("https://market.android.com/details?id=APP_PACKAGE_NAME"));
            startActivity(browserIntent);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(intent!= null)
        {
            switch (intent.getAction())
            {
                case "com.google.zxing.client.android.SCAN":
                {
                    if (requestCode == 1)
                    {
                        if (resultCode == RESULT_OK) {

                            String contents = intent.getStringExtra("SCAN_RESULT");
                            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                            // Handle successful scan
                        }
                    }
                }
                case RecognizerIntent.ACTION_RECOGNIZE_SPEECH:
                {
                    if (requestCode == 1  && resultCode == RESULT_OK)
                    {
                        ArrayList<String> thingsYouSaid = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        searchView.setQuery(thingsYouSaid.get(0), true);
                    }
                }
            }
        }
    }
}


/*TabHost tabHost = getTabHost();

        TabSpec technicalDetailsSpec = tabHost.newTabSpec("TechnicalDetails");
        technicalDetailsSpec.setIndicator("TechnicalDetails");
        Intent technicalDetailsIntent = new Intent(this, TechnicalDetailsActivity.class);
        technicalDetailsSpec.setContent(technicalDetailsIntent);

        TabSpec priceSpec = tabHost.newTabSpec("Price");
        priceSpec.setIndicator("Price");
        Intent priceIntent = new Intent(this, PriceActivity.class);
        priceSpec.setContent(priceIntent);

        TabSpec photospec = tabHost.newTabSpec("Photos");
        photospec.setIndicator("Photos");
        Intent photosIntent = new Intent(this, PhotoActivity.class);
        photospec.setContent(photosIntent);

        TabSpec linksSpec = tabHost.newTabSpec("Links");
        linksSpec.setIndicator("Links");
        Intent linksIntent = new Intent(this, LinksActivity.class);
        linksSpec.setContent(linksIntent);

        tabHost.addTab(technicalDetailsSpec);
        tabHost.addTab(priceSpec);
        tabHost.addTab(photospec);
        tabHost.addTab(linksSpec);*/
