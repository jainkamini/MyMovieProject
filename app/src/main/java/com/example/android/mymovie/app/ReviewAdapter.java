package com.example.android.mymovie.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kamini on 10/8/2015.
 */
public class ReviewAdapter extends ArrayAdapter<ReviewItem> {
    private Context context;
    private LayoutInflater inflater;

    //  private ArrayList Gridtem urls;
    //List<String> items;
    //   private String[] imageUrls;
    // private List urls = new ArrayList();
    ArrayList<ReviewItem> item = new ArrayList<ReviewItem>();


    public ReviewAdapter(Context context, ArrayList<ReviewItem> item) {
        super(context, R.layout.review_list,  item);



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


            convertView = LayoutInflater.from(context).inflate(R.layout.review_list, parent, false);
            // convertView = mInflater.inflate(R.layout.movie_list, null);
            holder = new ViewHolder();

            holder.contenttextView = (TextView) convertView.findViewById(R.id.list_review_text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //  holder.textView.setText(mData.get(position));

        ReviewItem data = item.get(position);
String Review="";


        if ((data.getmReviewContent()!=null) && (data.getmReviewAuthor()!=null))
        {
            Review =data.getmReviewContent()+" - "+(data.getmReviewAuthor());
            holder.contenttextView.setText(Review);
        }
        else {
            holder.contenttextView.setText(data.getmReviewContent());

        }

        return convertView;
    }


    public  class ViewHolder {

        public TextView contenttextView;


    }
}
