package com.example.android.mymovie.app;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


     private GridView mMoviesGrid;
     private ImageAdapter mMoviesAdapter;
    //public ArrayList<String> mPosterMoviePaths;
   // String mMovieJsonStr = null;

    public MainActivityFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add this line in order for this fragment to handle menu events.
        //setHasOptionsMenu(true);
       // new FeatchMovieTask().execute();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        // references to our images
         Integer[] mThumbIds ={
                R.drawable.download8, R.drawable.download7,
                R.drawable.download9, R.drawable.download1,

        };

        GridView mMoviesGrid  = (GridView) rootView.findViewById(R.id.movieGrid);
        mMoviesGrid.setAdapter(new ImageAdapter(getContext(),mThumbIds));

        return rootView;
    }





      //  int[] imageId =  {
        //        R.drawable.download8};
       /* mMoviesAdapter = new ImageAdapter(getActivity(),imageId); //"http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg");

        GridView mMoviesGrid  = (GridView) rootView.findViewById(R.id.movieGrid);
       // mMoviesAdapter=new ImageAdapter ();
        mMoviesGrid.setAdapter(mMoviesAdapter);
      //  ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_image);
       // Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").into(imageView);
     return rootView;
    }


   /* public class FeatchMovieTask extends AsyncTask<Void, Void, String> {

        private final String LOG_TAG = FeatchMovieTask.class.getSimpleName();
        @Override
        protected String doInBackground(Void... params) {

            // http://image.tmdb.org/t/p/w185/aBBQSC8ZECGn6Wh92gKDOakSC8p.jpg
            Log.v(LOG_TAG, "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg");
            return "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";


        }

        @Override
        protected void onPostExecute(String result) {


          //  if (result != null) {
             //   mMoviesAdapter.notifyDataSetChanged();
               // for (String resultStrs : result) {
                //    mMoviesAdapter = new ImageAdapter(getActivity(), result);
              //  }
         //   }
            super.onPostExecute( result);
           mMoviesAdapter = new ImageAdapter(getActivity(), result);
         //   mMoviesGrid.setAdapter(mMoviesAdapter);
            mMoviesAdapter.notifyDataSetChanged();


           // GridView gridView = (GridView)

            //gridView.setAdapter(mMoviesAdapter);
        }*/

    }


