package com.example.android.mymovie.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //this is new added
        //change palceholder to DetailFragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment  extends Fragment {

        public DetailFragment () {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            String LOG_TAG = DetailActivity.class.getSimpleName();
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra("Title")) {


                String mMovieTitle = (String) intent.getStringExtra("Title");
                ((TextView) rootView.findViewById(R.id.movietitle_text)).setText(mMovieTitle);
            }
            if (intent != null && intent.hasExtra("Overview")) {
                String mMovieOverview = (String) intent.getStringExtra("Overview");
                ((TextView) rootView.findViewById(R.id.movieoverview_text)).setText(mMovieOverview);
            }
            if (intent != null && intent.hasExtra("VoteAverage")) {
                String mMovieVoteAverage = (String) intent.getStringExtra("VoteAverage");
                ((TextView) rootView.findViewById(R.id.movievoteAverage_text)).setText(mMovieVoteAverage+"/10");
            }
            if (intent != null && intent.hasExtra("ReleaseDate")) {
              //  Log.v(LOG_TAG, (String) intent.getStringExtra("ReleaseDate"));
                String mMovieReleaseDate = (String) intent.getStringExtra("ReleaseDate");



                    Log.v(LOG_TAG, (String) mMovieReleaseDate);


                        mMovieReleaseDate = mMovieReleaseDate.substring(0, 4);


                    ((TextView) rootView.findViewById(R.id.moviereleasedate_text)).setText(mMovieReleaseDate);


            }
            if (intent != null && intent.hasExtra("ImagePoster")) {
                String mMoviePoster = (String)intent.getStringExtra("ImagePoster");

              ImageView imageView=(ImageView) rootView.findViewById(R.id.movieposter_image);
                 Context context=this.getContext();
                imageView.setAdjustViewBounds(true);
                Picasso.with(context).load( "http://image.tmdb.org/t/p/w185"+mMoviePoster).into(imageView);


            }
            /*Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra("movie")) {
                MovieItem movie = intent.getParcelableExtra("movie");
                ((TextView) rootView.findViewById(R.id.detail_text))
                        .setText(movie.getMovieTitle());
            }*/
            return rootView;
        }

    }
}
