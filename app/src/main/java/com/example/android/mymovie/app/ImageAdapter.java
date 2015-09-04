package com.example.android.mymovie.app;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParsePosition;
import java.util.ArrayList;

/**
 * Created by Kamini on 9/3/2015.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private String imageUrls;



    public ImageAdapter(Context mContext, String imageUrls) {
        this.mContext = mContext;
        this.imageUrls = imageUrls;
    }


    @Override
    public int getCount() {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(mContext);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.fragment_main, null);


            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.movie_image);

            Picasso.with(mContext).load(imageUrls).into(imageView);



        } else {
            gridView = (View) convertView;
        }

        return gridView;

       /* ImageView imageView;
        imageView = (ImageView) convertView.findViewById(R.id.movie_image);

        Picasso.with(mContext).load(imageUrls).into(imageView);
        return imageView;*/
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
}
