package com.example.android.mymovie.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Kamini on 10/5/2015.
 */
public class TrailerAdapter extends ArrayAdapter<TrailerItem>
{
    private Context context;
    private LayoutInflater inflater;

    //  private ArrayList Gridtem urls;
    //List<String> items;
    //   private String[] imageUrls;
    // private List urls = new ArrayList();
    ArrayList<TrailerItem> item = new ArrayList<TrailerItem>();


    public TrailerAdapter(Context context, ArrayList<TrailerItem> item) {
        super(context, R.layout.trailer_list,  item);



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


            convertView = LayoutInflater.from(context).inflate(R.layout.trailer_list, parent, false);
            // convertView = mInflater.inflate(R.layout.movie_list, null);
            holder = new ViewHolder();
            holder.trailerimageView = (ImageView) convertView.findViewById(R.id.trailer_image);
            holder.keytextView = (TextView) convertView.findViewById(R.id.list_trailerkey_text);
            holder.nametextView = (TextView) convertView.findViewById(R.id.list_trailername_text);
            //holder. imageView.setAdjustViewBounds(true);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //  holder.textView.setText(mData.get(position));

        TrailerItem data = item.get(position);


        //  holder. imageView.setImageResource(mThumbIds[position]);
        holder .trailerimageView.setAdjustViewBounds(true);
        holder.keytextView.setText(data.getmTrailerKey());
        holder.nametextView.setText(data.getmTrailerName());
    //    Picasso.with(context).load("http://image.tmdb.org/t/p/w185" + data.getMovieImageurl()).into(holder.trailerimageView);
        // Picasso.with(context).load(imageUrls[position]).fit().into(holder.imageView);
        // Picasso.with(context).load(items[position].tostring).fit().into(holder.imageView);

        return convertView;
    }


    public  class ViewHolder {
        public ImageView trailerimageView;
        public TextView keytextView;
        public TextView nametextView;

    }
}
