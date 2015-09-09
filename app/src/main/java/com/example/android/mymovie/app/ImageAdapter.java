package com.example.android.mymovie.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
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
  private   Integer[] mThumbIds;

    public ImageAdapter(Context c,Integer [] ThumbIds  ) {
        mContext = c;
        mThumbIds=ThumbIds;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    /*// create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes

           // imageView=(ImageView) convertView.findViewById(R.id.movie_image);

            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

      //  imageView.setImageResource(mThumbIds[position]);
       // Picasso.with(mContext).load(mThumbIds[position]).into(imageView);
        Picasso.with(mContext).load(mThumbIds[position]).into(imageView);
        return imageView;
    }*/


    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder = null;

        if (convertView == null) {


            convertView = LayoutInflater.from(mContext).inflate(R.layout.movie_list, parent, false);
           // convertView = mInflater.inflate(R.layout.movie_list, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView)convertView.findViewById(R.id.movie_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

      //  holder.textView.setText(mData.get(position));


        holder. imageView.setImageResource(mThumbIds[position]);
        // Picasso.with(mContext).load(mThumbIds[position]).into(imageView);
       Picasso.with(mContext).load(mThumbIds[position]).into(holder.imageView);
       // Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + mThumbIds[position]).into(holder.imageView);
        //Picasso.with(getContext())
          //      .load(artistViews.images.get(1).url)
            //    .resize(200, 200)
              //  .into(imageView);
        return convertView;
    }


    public static class ViewHolder {
        public ImageView imageView;
    }
   /* // references to our images
    private Integer[] mThumbIds = {
            R.drawable.download8, R.drawable.download7,
            R.drawable.download9, R.drawable.download1,

    };*/
}
