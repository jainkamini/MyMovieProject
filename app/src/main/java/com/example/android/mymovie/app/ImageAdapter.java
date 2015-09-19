package com.example.android.mymovie.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Kamini on 9/9/2015.
 */
public class ImageAdapter extends ArrayAdapter<MovieItem>
{
    private Context context;
    private LayoutInflater inflater;

  //  private ArrayList Gridtem urls;
    //List<String> items;
 //   private String[] imageUrls;
   // private List urls = new ArrayList();
  ArrayList<MovieItem> item = new ArrayList<MovieItem>();


    public ImageAdapter(Context context, ArrayList<MovieItem> item) {
        super(context, R.layout.movie_list,  item);



        this.context = context;
     //  this.imageUrls = imageUrls;
        this.item = item;

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
                //holder. imageView.setAdjustViewBounds(true);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //  holder.textView.setText(mData.get(position));

        MovieItem data = item.get(position);


        //  holder. imageView.setImageResource(mThumbIds[position]);
      // holder .imageView.setAdjustViewBounds(true);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185" + data.getMovieImageurl()).into(holder.imageView);
           // Picasso.with(context).load(imageUrls[position]).fit().into(holder.imageView);
       // Picasso.with(context).load(items[position].tostring).fit().into(holder.imageView);

            return convertView;
        }


    public  class ViewHolder {
        public ImageView imageView;
    }
}