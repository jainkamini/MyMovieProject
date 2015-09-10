package com.example.android.mymovie.app;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import android.content.Loader;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamini on 9/8/2015.
 */
public class testclass  extends ArrayAdapter<String> {

    private Context mContext;
    public String[] myPosters;


    public testclass(Context c, ArrayList<String> data) {
        super(c, R.layout.activity_main, data);
        this.mContext = c;

       // String[] myPosters = new String[data.size()];
        //data.toArray(myPosters);
        //Log.v("@ImgAdapt poster sz:", String.valueOf(myPosters.length));
    }

    public int getCount() {
        //return mThumbIds.length;
        //or myPosters size if i can get it to be not null
//        Log.v("@@@getCnt poster sz is:", String.valueOf(myPosters.length));
        return myPosters.length;
    }







    // create a new ImageView for each item referenced by the Adapter
    @Override
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


        //  holder. imageView.setImageResource(mThumbIds[position]);

        Picasso.with(mContext).load("http://i.imgur.com/DvpvklR.png").into(holder. imageView);
       /* Log.v("@@@movie path is:","hi" );
        ImageView imageView;
     //   Log.v("@are we here?", "nope");
        if (convertView == null) {  // if it's not recycled, initialize some attributes

            imageView = new ImageView(mContext);
            imageView.setPadding(0, 0, 0, 0);
            imageView.setAdjustViewBounds(true);

        } else {
            imageView = (ImageView) convertView;
        }

        imageView = (ImageView)convertView.findViewById(R.id.movie_image);

       // imageView= Loader(this, "http://developer.android.com/images/dialog_buttons.png");
///imageView.setImageResource(R.drawable.download1);
       String DB_base_Url = "http://image.tmdb.org/t/p/w185/";
        String builtUri = DB_base_Url += myPosters[position];
        final String QUERY_PARAM = "sort_by";
     //   Log.v("@@@movie path is:", String.valueOf(myPosters[position]));
       // final String FETCHMOVIE_BASE_URL ="http://api.themoviedb.org/3/discover/movie?";
        Uri builtUri1 = Uri.parse(DB_base_Url).buildUpon().build();
//imageView.setImageURI(builtUri1);
      //  imageView.setImageDrawable("drawable://" + ().getIdentifier("img"+i, "drawable", getPackageName()));
      //  Drawable drawable = LoadImageFromWebOperations("http://www.androidpeople.com/wp-content/uploads/2010/03/android.png");

      //  Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + myPosters[position]).into(imageView);
     //  Picasso.with(mContext).load(builtUri1).into(imageView);
        Picasso.with(mContext).load("http://i.imgur.com/DvpvklR.png").into(imageView);
      //  Picasso.with(mContext)                .load(R.drawable.download1)                .into(imageView);

        return imageView;*/
        return convertView;
    }


    public  class ViewHolder {
        public ImageView imageView;
    }
}
