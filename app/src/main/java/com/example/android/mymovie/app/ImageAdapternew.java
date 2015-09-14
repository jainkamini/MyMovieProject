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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamini on 9/9/2015.
 */
public class ImageAdapternew extends ArrayAdapter
{
    private Context context;
    private LayoutInflater inflater;

   // private ArrayList MovieDataStructure mObjects;
    //List<String> items;
 //   private String[] imageUrls;
    private List urls = new ArrayList();

    public ImageAdapternew(Context context, List<String> urlslist) {
        super(context, R.layout.movie_list,  urlslist);



        this.context = context;
     //  this.imageUrls = imageUrls;
        this.urls = urlslist;

        inflater = LayoutInflater.from(context);
    }


    @Override



        public View getView ( int position, View convertView, ViewGroup parent){
            ViewHolder holder = null;
        String url = getItem(position).toString();
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
        Picasso.with(context).load(url).fit().into(holder.imageView);
           // Picasso.with(context).load(imageUrls[position]).fit().into(holder.imageView);
       // Picasso.with(context).load(items[position].tostring).fit().into(holder.imageView);

            return convertView;
        }


    public  class ViewHolder {
        public ImageView imageView;
    }
}