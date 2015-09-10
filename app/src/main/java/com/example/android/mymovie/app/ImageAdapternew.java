package com.example.android.mymovie.app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Kamini on 9/9/2015.
 */
public class ImageAdapternew extends ArrayAdapter
{
    private Context context;
    private LayoutInflater inflater;
    //List<String> items;
    private String[] imageUrls;

    public ImageAdapternew(Context context, String[] imageUrls) {
        super(context, R.layout.movie_list, imageUrls);

        this.context = context;
       this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);
    }

    @Override



        public View getView ( int position, View convertView, ViewGroup parent){
            ViewHolder holder = null;

            if (convertView == null) {


                convertView = LayoutInflater.from(context).inflate(R.layout.movie_list, parent, false);
                // convertView = mInflater.inflate(R.layout.movie_list, null);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.movie_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //  holder.textView.setText(mData.get(position));


            //  holder. imageView.setImageResource(mThumbIds[position]);

            Picasso.with(context).load(imageUrls[position]).fit().into(holder.imageView);

            return convertView;
        }


    public  class ViewHolder {
        public ImageView imageView;
    }
}