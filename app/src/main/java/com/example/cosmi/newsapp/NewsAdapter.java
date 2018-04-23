package com.example.cosmi.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by cosmi on 3/20/2018.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(MainActivity context, ArrayList<News> arrayOfNews){
        super(context, 0, arrayOfNews);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        final News currentNews = getItem(position);

        TextView section = listItemView.findViewById(R.id.section);
        section.setText(currentNews.getSection());

        TextView title = listItemView.findViewById(R.id.title);
        title.setText(currentNews.getTitle());

        TextView data = listItemView.findViewById(R.id.date);
        data.setText(currentNews.getPublicationDate());

        RelativeLayout relativeLayout = (RelativeLayout) listItemView.findViewById(R.id.relative_layout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send an intent with the current earthquake url
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentNews.getUrl()));
                view.getContext().startActivity(intent);
            }
        });


        return listItemView;
    }

}
