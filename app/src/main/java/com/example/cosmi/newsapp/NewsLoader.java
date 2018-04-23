package com.example.cosmi.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cosmi on 3/20/2018.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String url;

    public NewsLoader(Context context, String passedURL) {
        super(context);
        this.url = passedURL;
    }

    @Override
    protected void onStartLoading() {
        Log.e("Tag", "entering onStartLoading");
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        Log.e("Tag", "entering loadInBackground");
        if (url == null) {
            return null;
        }

        ArrayList<News> news = QueryUtils.fetchNewsData(url);
        return news;
    }

}
