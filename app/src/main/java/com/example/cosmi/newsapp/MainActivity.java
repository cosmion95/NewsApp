package com.example.cosmi.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String NEWS_REQUEST_URL = "https://content.guardianapis.com/search?api-key=test";

    private NewsAdapter newsAdapter;
    ArrayList<News> newsDataObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        if (isConnected) {
            newsDataObjects = new ArrayList<>();
            newsAdapter = new NewsAdapter(this, newsDataObjects);

            LoaderManager loaderManager = getLoaderManager();
            Log.e("Tag", "initializing loader");
            loaderManager.initLoader(0, null, this);
        }
        else {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
            TextView emptyView = (TextView) findViewById(R.id.empty_view);
            progressBar.setVisibility(View.GONE);
            emptyView.setText("No internet connection.");
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    private void updateUi(ArrayList<News> result) {
        // ArrayList<Earthquake> earthquakes = QueryUtils.extractFeatureFromJson(USGS_REQUEST_URL);

        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = (ListView) findViewById(R.id.news_list);
        TextView emptyView = (TextView) findViewById(R.id.empty_view);

        // Create a new {@link ArrayAdapter} of earthquakes
        if (result != null && !result.isEmpty()) {
            newsAdapter.addAll(result);
        }

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(newsAdapter);
        Log.e("tag","setting the empty view");
        newsListView.setEmptyView(emptyView);


    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        // TODO: Create a new loader for the given URL
        Log.e("Tag", "entering OnCreateLoader");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String orderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(NEWS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("order-by", orderBy);



        return new NewsLoader(this, uriBuilder.toString());

    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        // TODO: Update the UI with the result
        Log.e("Tag", "entering onLoadFinished");
        if (newsAdapter != null) {
            newsDataObjects.clear();
        }

        if (news != null && !news.isEmpty()){
            newsDataObjects.addAll(news);
        }
        updateUi(newsDataObjects);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // TODO: Loader reset, so we can clear out our existing data.
        Log.e("Tag", "entering onLoaderReset");
        newsAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
