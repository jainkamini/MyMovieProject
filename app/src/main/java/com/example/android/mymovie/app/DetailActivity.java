package com.example.android.mymovie.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mymovie.app.data.MovieContract;
import com.example.android.mymovie.app.data.MovieProvider;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;


public class DetailActivity extends ActionBarActivity {

    public static String mMovieId;
   // private static ArrayAdapter<String> mTrailerAdapter;

    public  static  TrailerAdapter mTrailerAdapter;
    private static ReviewAdapter  mReviewAdapter;
    public static  String mMovieTitle;
    public static  String mMovieOverview;
    public static  String mMovieVoteAverage;
    public static   String mMovieReleaseDate;
    public static  String mMoviePoster;
 public static    MovieItem movieItem;
   // public  ArrayList<MovieItem> MovieItem = new ArrayList();

    public static String[] MovieReview ;
    public static String preffaram;
    public static String mShareTrailerKey;

    public static ArrayList<TrailerItem> TrailerList = new ArrayList();
    public static ArrayList<ReviewItem> ReviewList = new ArrayList();

   // public static ArrayList<String> MovieTrailer = new ArrayList();
  //  public static ArrayList<String> MovieReview = new ArrayList();

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
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("MovieKey", movieItem);
      outState.putParcelableArrayList("TrailerKey", TrailerList);
        outState.putParcelableArrayList("ReviewKey", ReviewList);

        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        movieItem=  savedInstanceState.getParcelable("MovieKey");
        TrailerList=savedInstanceState.getParcelableArrayList("TrailerKey");
        ReviewList=savedInstanceState.getParcelableArrayList("ReviewKey");
        super.onRestoreInstanceState(savedInstanceState);
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
        private static final String LOG_TAG = DetailFragment.class.getSimpleName();
        private static final String MOVIE_TRAILER_SHARE = "http://www.youtube.com/watch?v=";

        public DetailFragment() {
            setHasOptionsMenu(true);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            String LOG_TAG = DetailActivity.class.getSimpleName();
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra("MovieItem")) {
                Bundle data = intent.getExtras();

                movieItem = (MovieItem) data.getParcelable("MovieItem");

                ((TextView) rootView.findViewById(R.id.movietitle_text)).setText(movieItem.getMovieTitle());
                ((TextView) rootView.findViewById(R.id.movieoverview_text)).setText(movieItem.getmMovieOverView());
                ((TextView) rootView.findViewById(R.id.movievoteAverage_text)).setText(movieItem.getmMovieVoteAverage() + "/10");
                mMovieId = movieItem.getmMovieId();
                mMovieReleaseDate = movieItem.getmMovieReleaseDate().substring(0, 4);
                ((TextView) rootView.findViewById(R.id.moviereleasedate_text)).setText(mMovieReleaseDate);
                ImageView imageView = (ImageView) rootView.findViewById(R.id.movieposter_image);
                Context context = this.getContext();
                imageView.setAdjustViewBounds(true);
                Picasso.with(context).load("http://image.tmdb.org/t/p/w185" + movieItem.getmMoviePoster()).into(imageView);


            }






            if (intent != null && intent.hasExtra("PrefParm"))
            {
                preffaram=(String)intent.getStringExtra("PrefParm");
            }




            mTrailerAdapter = new TrailerAdapter(this.getActivity(), TrailerList);


            ListView listView = (ListView) rootView.findViewById(R.id.listview_trailer);
            listView.setAdapter(mTrailerAdapter);





           mReviewAdapter = new ReviewAdapter(this.getActivity(), ReviewList);


            ListView listViewreview = (ListView) rootView.findViewById(R.id.listview_review);
            listViewreview.setAdapter(mReviewAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    TrailerItem movieTrailer = (TrailerItem) mTrailerAdapter.getItem(position);

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + movieTrailer.getmTrailerKey())));

                }
            });

            Button favourite_button = (Button) rootView.findViewById(R.id.favourite_button);

            favourite_button.setOnClickListener(new View.OnClickListener() {
                //Insert data in table when click on favorit
                //first check row is exist if yes
                //then update else insert

                @Override
                public void onClick(View arg0) {
 //if (checkFavorite("Movie")==false)  {
                    Cursor cursor = getContext().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null,
                            MovieContract.MovieEntry.COLUMN_MOVIE_ID+"="+mMovieId,
                            null,null);
                    if (cursor.getCount()==0 ) {
                        ContentValues movieValues = new ContentValues();
                        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID,
                                mMovieId);
                        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER,
                                mMoviePoster);
                        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_RATING,
                                mMovieVoteAverage);
                        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_DATE,
                                mMovieReleaseDate);
                        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW,
                                mMovieOverview);
                        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE,
                                mMovieTitle);

                        Uri uri = getContext().getContentResolver().insert(
                                MovieContract.MovieEntry.CONTENT_URI, movieValues);

                    }
                    else
                    {

                        ContentValues movieValues = new ContentValues();
                        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID,
                                mMovieId);
                        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER,
                                mMoviePoster);
                        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_RATING,
                                mMovieVoteAverage);
                        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_DATE,
                                mMovieReleaseDate);
                        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW,
                                mMovieOverview);
                        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE,
                                mMovieTitle);

                        int  uri = getContext().getContentResolver().update(
                                MovieContract.MovieEntry.CONTENT_URI, movieValues, MovieContract.MovieEntry.COLUMN_MOVIE_ID + "=" + mMovieId,null);

                    }
//} int i;

                    int i;




                  //  if (MovieTrailer != null) {
                        for (i = 0; i < mTrailerAdapter.getCount(); i++) {
                            Cursor cursorTrailer = getContext().getContentResolver().query(MovieContract.TrailerEntry.CONTENT_URI, null,
                                    MovieContract.TrailerEntry.COLUMN_MOVIEID_KEY + "=" + mMovieId + " AND " +
                                            MovieContract.TrailerEntry.COLUMN_MOVIE_KEY + "="+"'" +  mTrailerAdapter.getItem(i).getmTrailerKey()+ "'" +  " AND " +
                                    MovieContract.TrailerEntry.COLUMN_TRAILER_NMAE + "="+"'" +  mTrailerAdapter.getItem(i).getmTrailerName()+ "'"  ,
                                    null, null);
                            if (cursorTrailer.getCount() == 0) {
                                ContentValues TrailerValues = new ContentValues();
                                TrailerValues.put(MovieContract.TrailerEntry.COLUMN_MOVIEID_KEY,
                                        mMovieId);
                                TrailerValues.put(MovieContract.TrailerEntry.COLUMN_MOVIE_KEY,
                                        mTrailerAdapter.getItem(i).getmTrailerKey()    );
                                TrailerValues.put(MovieContract.TrailerEntry.COLUMN_TRAILER_NMAE,
                                        mTrailerAdapter.getItem(i).getmTrailerName()    );


                                Uri uri1 = getContext().getContentResolver().insert(
                                        MovieContract.TrailerEntry.CONTENT_URI, TrailerValues);
                            } else {

                                ContentValues TrailerValues = new ContentValues();
                                TrailerValues.put(MovieContract.TrailerEntry.COLUMN_MOVIEID_KEY,
                                        mMovieId);
                                TrailerValues.put(MovieContract.TrailerEntry.COLUMN_MOVIE_KEY,
                                        mTrailerAdapter.getItem(i).getmTrailerKey());
                                TrailerValues.put(MovieContract.TrailerEntry.COLUMN_TRAILER_NMAE,
                                        mTrailerAdapter.getItem(i).getmTrailerName());

                                int uri1 = getContext().getContentResolver().update(
                                        MovieContract.TrailerEntry.CONTENT_URI, TrailerValues, MovieContract.TrailerEntry.COLUMN_MOVIEID_KEY + "=" + mMovieId +
                                                " AND " + MovieContract.TrailerEntry.COLUMN_MOVIE_KEY + "="+" ' " + mTrailerAdapter.getItem(i).getmTrailerKey()+ "' " +
                                        " AND " + MovieContract.TrailerEntry.COLUMN_TRAILER_NMAE + "="+" ' " + mTrailerAdapter.getItem(i).getmTrailerName()+ "' "
                                        , null);
                            }

                            // }

                            //         }}
                        }
                  //  }
        //            if (checkFavorite("Review")==false) {


    for (i = 0; i < mTrailerAdapter.getCount(); i++) {
        Cursor cursorReview = getContext().getContentResolver().query(MovieContract.ReviewEntry.CONTENT_URI, null,
               MovieContract.ReviewEntry.COLUMN_MOVIEID_KEY + "=" + mMovieId ,
               null, null);
       if (cursorReview.getCount() == 0) {
            ContentValues ReviewValues = new ContentValues();
            ReviewValues.put(MovieContract.ReviewEntry.COLUMN_MOVIEID_KEY,
                    mMovieId);
            ReviewValues.put(MovieContract.ReviewEntry.COLUMN_MOVIE_AUTHOR,
                    mReviewAdapter.getItem(i).getmReviewAuthor());
            ReviewValues.put(MovieContract.ReviewEntry.COLUMN_MOVIE_CONTENT,
                    mReviewAdapter.getItem(i).getmReviewContent());

            Uri uri2 = getContext().getContentResolver().insert(
                    MovieContract.ReviewEntry.CONTENT_URI, ReviewValues);


           /*else {

           ContentValues ReviewValues = new ContentValues();
        ReviewValues.put(MovieContract.ReviewEntry.COLUMN_MOVIEID_KEY,
                   mMovieId);
            ReviewValues.put(MovieContract.ReviewEntry.COLUMN_MOVIE_AUTHOR,
        MovieReview[i]);
            ReviewValues.put(MovieContract.ReviewEntry.COLUMN_MOVIE_CONTENT,
        MovieReview[i]);
           //   if (checkFavorite("Review")==false) {
                  int uri1 = getContext().getContentResolver().update(
                          MovieContract.ReviewEntry.CONTENT_URI, ReviewValues, MovieContract.ReviewEntry.COLUMN_MOVIEID_KEY + "=" + mMovieId + " AND " + MovieContract.ReviewEntry.COLUMN_MOVIE_CONTENT + "=" + " ' " + MovieReview[i] + "' ", null);

       }*/

    }
}
               //  }   }


                    Toast.makeText(getContext(),
                            " Favorite Added ", Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(),
                            " Favorite Added ", Toast.LENGTH_LONG).show();
                }

            });





            return rootView;

        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Inflate the menu; this adds items to the action bar if it is present.
            inflater.inflate(R.menu.detailfragemnt, menu);

            // Retrieve the share menu item
            MenuItem menuItem = menu.findItem(R.id.action_share);

            // Get the provider and hold onto it to set/change the share intent.
            ShareActionProvider mShareActionProvider =
                    (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

            // Attach an intent to this ShareActionProvider.  You can update this at any time,
            // like when the user selects a new piece of data they might like to share.
            if (mShareActionProvider != null ) {
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            } else {
                Log.d(LOG_TAG, "Share Action Provider is null?");
            }
        }

        private Intent createShareForecastIntent() {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    MOVIE_TRAILER_SHARE + mShareTrailerKey);
            return shareIntent;
        }

        public void onStart() {
            super.onStart();
          //  updateMovie();
           FetchTrailerTask TrailerTask= new FetchTrailerTask();
           TrailerTask.execute(mMovieId);
            FetchReviewTask ReviewTask= new FetchReviewTask();
            ReviewTask.execute(mMovieId);


        }




        public class FetchTrailerTask extends AsyncTask<String, Void,  List<TrailerItem>> {

            private final String LOG_TAG = FetchTrailerTask.class.getSimpleName();

            //Take the String representing the complete trailer in JSON Format and
            // pull out the data we need to construct the Strings needed for the wireframes.

            // Fortunately parsing is easy:  constructor takes the JSON string and converts it
            //into an Object hierarchy for us.

            private  List<TrailerItem> getMovieDataFromJson(String fetchtMovieJsonStr)
                    throws JSONException {

                // These are the names of the JSON objects that need to be extracted.
                final String TMB_RESULTS = "results";
                final String TMB_KEY = "key";
                final String TMB_NAME="name";

                JSONObject fetchtMovieJson = new JSONObject(fetchtMovieJsonStr);
                JSONArray movieArray = fetchtMovieJson.getJSONArray(TMB_RESULTS);

                int numMovies = movieArray.length();
                String[] resultStrs = new String[numMovies];
                List<TrailerItem> Trailerlistitem = new ArrayList<>();
              //  Log.v(LOG_TAG,resultStrs.toString());
                for (int i = 0; i < movieArray.length(); i++) {





                    JSONObject movieDetail = movieArray.getJSONObject(i);
                    //First taralier for share

if (i==0)
{
    mShareTrailerKey=movieDetail.getString(TMB_KEY);


}

                    Trailerlistitem.add(new TrailerItem(movieDetail.getString(TMB_KEY),movieDetail.getString(TMB_NAME)));



                   // Log.v(LOG_TAG, "Movie Trailer: " , );
                }

                return Trailerlistitem;
            }

            //fetch trailer from table

            private  List<TrailerItem> getMovieTrailerFromDB()
                    throws DataFormatException {



                int i=0;
                Cursor cursor = getContext().getContentResolver().query(MovieContract.TrailerEntry.CONTENT_URI,null,MovieContract.TrailerEntry.COLUMN_MOVIEID_KEY+"="+ mMovieId,null,null,null);
                String[] resultStrs = new String[cursor.getCount()];
                List<TrailerItem> Trailerlistitem = new ArrayList<>();
                if (!cursor.moveToFirst()) {
                    Log.v(LOG_TAG, "Favorite no found yet!");
                    // Toast.makeText(getContext(), "Favorite no found yet!", Toast.LENGTH_LONG).show();

                } else {

                    do {

                        if (i==0)
                        {
                            mShareTrailerKey=(cursor.getString(cursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_MOVIE_KEY)));
                        }
                        Trailerlistitem.add(new TrailerItem((cursor.getString(cursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_MOVIE_KEY))),
                                cursor.getString(cursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_TRAILER_NMAE))));

            //            resultStrs[i++]   =(cursor.getString(cursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_MOVIE_KEY)));
                      //  trailerItem.setmTrailerKey(cursor.getString(cursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_MOVIE_KEY)));
                        //trailerItem.setmTrailerName(cursor.getString(cursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_TRAILER_NMAE)));

                             //   trailerList.add(trailerItem);


i++;

                    } while (cursor.moveToNext());

                    //   Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();

                }

                return Trailerlistitem;

            }

            @Override
            protected  List<TrailerItem> doInBackground(String... params) {
                // These two need to be declared outside the try/catch
                // so that they can be closed in the finally block.


                // If there's no sort order or highest rating there's nothing to look up.  Verify size of params.
                if (params.length == 0) {
                    return null;
                }
//if favorites is select then fetch data from table
                if ("favorites".equals(preffaram)) {

                    Log.v(LOG_TAG, preffaram);
                    try {
                        return getMovieTrailerFromDB();
                    } catch (DataFormatException e) {
                        Log.e(LOG_TAG, e.getMessage(), e);
                        e.printStackTrace();
                    }

                    return null;
                }
                else {
                    HttpURLConnection urlConnection = null;
                    BufferedReader reader = null;

                    // Will contain the raw JSON response as a string.
                    String fetchtMovieJsonStr = null;
                    // String format = "json";
                    //  String apikey = "xxxx";
                    String apikey = "b85cf4603ce5916a993dd400866808bc";


                    try {
                        // Construct the URL for the Fetchmovie query
                        // Possible parameters are avaiable at OWM's Fetchmovie API page, at
                        // http://openweathermap.org/API#forecast

                        final String FETCHMOVIE_BASE_URL = " http://api.themoviedb.org/3/movie/" + mMovieId + "/videos?";
                        final String QUERY_PARAM = "sort_by";
                        final String API_KEY = "api_key";
                        //


                        // Create the request to MovieDetails, and open the connection
                        Uri builtUri = Uri.parse(FETCHMOVIE_BASE_URL).buildUpon()
                                //  .appendQueryParameter(QUERY_PARAM, params[0])
                                .appendQueryParameter(API_KEY, apikey).build();
                        URL url = new URL(builtUri.toString());
                        Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.connect();

                        // Read the input stream into a String
                        InputStream inputStream = urlConnection.getInputStream();
                        StringBuffer buffer = new StringBuffer();
                        if (inputStream == null) {
                            // Nothing to do.
                            return null;
                        }
                        reader = new BufferedReader(new InputStreamReader(inputStream));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                            // But it does make debugging a *lot* easier if you print out the completed
                            // buffer for debugging.
                            buffer.append(line + "\n");
                        }

                        if (buffer.length() == 0) {
                            // Stream was empty.  No point in parsing.
                            return null;
                        }
                        fetchtMovieJsonStr = buffer.toString();
                        //   Log.v(LOG_TAG, "Fetchmovie string: " + fetchtMovieJsonStr);
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Error ", e);
                        // If the code didn't successfully get the movie data, there's no point in attemping
                        // to parse it.


                    } finally {
                        if (urlConnection != null) {
                            urlConnection.disconnect();
                        }
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (final IOException e) {
                                Log.e(LOG_TAG, "Error closing stream", e);
                            }
                        }
                    }
                    try {
                        return getMovieDataFromJson(fetchtMovieJsonStr);
                    } catch (JSONException e) {
                        Log.e(LOG_TAG, e.getMessage(), e);
                        e.printStackTrace();
                    }

                    // This will only happen if there was an error getting or parsing the forecast.
                    return null;
                }
            }


            protected void onPostExecute(List<TrailerItem> Trailerlistitem) {
                if (Trailerlistitem!=null) {

                    mTrailerAdapter.clear();


                        mTrailerAdapter.addAll(Trailerlistitem);
                    mTrailerAdapter.notifyDataSetChanged();



                }
            }
        }

        public class FetchReviewTask extends AsyncTask<String, Void,  List<ReviewItem>> {

            private final String LOG_TAG = FetchReviewTask.class.getSimpleName();

            //Take the String representing the complete reviews in JSON Format and
            // pull out the data we need to construct the Strings needed for the wireframes.

            // Fortunately parsing is easy:  constructor takes the JSON string and converts it
            //into an Object hierarchy for us.

            private   List<ReviewItem> getMovieDataFromJson(String fetchtMovieJsonStr)
                    throws JSONException {

                // These are the names of the JSON objects that need to be extracted.
                final String TMB_RESULTS = "results";
                final String TMB_AUTHOR = "author";
                final String TMB_CONTENT = "content";

                JSONObject fetchtMovieJson = new JSONObject(fetchtMovieJsonStr);
                JSONArray movieArray = fetchtMovieJson.getJSONArray(TMB_RESULTS);

                int numMovies = movieArray.length();
                String[] resultStrs = new String[numMovies];
                List<ReviewItem> reviewitem=new ArrayList<>();
                //  Log.v(LOG_TAG,resultStrs.toString());
                for (int i = 0; i < movieArray.length(); i++) {





                    JSONObject movieDetail = movieArray.getJSONObject(i);
                    reviewitem.add(new ReviewItem(movieDetail.getString(TMB_AUTHOR),movieDetail.getString(TMB_CONTENT) ));

                 //   resultStrs[i] =  movieDetail.getString(TMB_CONTENT) +" - "+movieDetail.getString(TMB_AUTHOR) ;

                    // Log.v(LOG_TAG, movieDetail.getString(TMB_KEY).toString());

                }


                return reviewitem;
            }

            private  List<ReviewItem> getMovieReviewFromDB()
                    throws DataFormatException {



                int i=0;
                Cursor cursor = getContext().getContentResolver().query(MovieContract.ReviewEntry.CONTENT_URI,null,MovieContract.ReviewEntry.COLUMN_MOVIEID_KEY+"="+ mMovieId,null,null,null);
                String[] resultStrs = new String[cursor.getCount()];
                List<ReviewItem> reviewitem=new ArrayList<>();
                if (!cursor.moveToFirst()) {
                    Log.v(LOG_TAG, "Favorite no found yet!");
                    // Toast.makeText(getContext(), "Favorite no found yet!", Toast.LENGTH_LONG).show();

                } else {

                    do {

                        reviewitem.add(new ReviewItem((cursor.getString(cursor.getColumnIndex(MovieContract.ReviewEntry.COLUMN_MOVIE_AUTHOR))),
                                (cursor.getString(cursor.getColumnIndex(MovieContract.ReviewEntry.COLUMN_MOVIE_CONTENT)))));
                     //   resultStrs[i++]   =(cursor.getString(cursor.getColumnIndex(MovieContract.ReviewEntry.COLUMN_MOVIE_CONTENT)));

i++;



                    } while (cursor.moveToNext());

                    //   Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();

                }

                return reviewitem;

            }
            @Override
            protected  List<ReviewItem> doInBackground(String... params) {
                // These two need to be declared outside the try/catch
                // so that they can be closed in the finally block.


                // If there's no sort order or highest rating there's nothing to look up.  Verify size of params.
                if (params.length == 0) {
                    return null;
                }
                if ("favorites".equals(preffaram)) {

                    Log.v(LOG_TAG, preffaram);
                    try {
                        return getMovieReviewFromDB();
                    } catch (DataFormatException e) {
                        Log.e(LOG_TAG, e.getMessage(), e);
                        e.printStackTrace();
                    }

                    return null;
                } else {
                    HttpURLConnection urlConnection = null;
                    BufferedReader reader = null;

                    // Will contain the raw JSON response as a string.
                    String fetchtMovieJsonStr = null;
                    // String format = "json";
                    //  String apikey = "xxxx";
                    String apikey = "b85cf4603ce5916a993dd400866808bc";


                    try {
                        // Construct the URL for the Fetchmovie query
                        // Possible parameters are avaiable at OWM's Fetchmovie API page, at
                        // http://openweathermap.org/API#forecast

                        final String FETCHMOVIE_BASE_URL = " http://api.themoviedb.org/3/movie/" + mMovieId + "/reviews?";
                        final String QUERY_PARAM = "sort_by";
                        final String API_KEY = "api_key";
                        //


                        // Create the request to MovieDetails, and open the connection
                        Uri builtUri = Uri.parse(FETCHMOVIE_BASE_URL).buildUpon()
                                //  .appendQueryParameter(QUERY_PARAM, params[0])
                                .appendQueryParameter(API_KEY, apikey).build();
                        URL url = new URL(builtUri.toString());
                        Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.connect();

                        // Read the input stream into a String
                        InputStream inputStream = urlConnection.getInputStream();
                        StringBuffer buffer = new StringBuffer();
                        if (inputStream == null) {
                            // Nothing to do.
                            return null;
                        }
                        reader = new BufferedReader(new InputStreamReader(inputStream));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                            // But it does make debugging a *lot* easier if you print out the completed
                            // buffer for debugging.
                            buffer.append(line + "\n");
                        }

                        if (buffer.length() == 0) {
                            // Stream was empty.  No point in parsing.
                            return null;
                        }
                        fetchtMovieJsonStr = buffer.toString();
                        //   Log.v(LOG_TAG, "Fetchmovie string: " + fetchtMovieJsonStr);
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Error ", e);
                        // If the code didn't successfully get the movie data, there's no point in attemping
                        // to parse it.


                    } finally {
                        if (urlConnection != null) {
                            urlConnection.disconnect();
                        }
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (final IOException e) {
                                Log.e(LOG_TAG, "Error closing stream", e);
                            }
                        }
                    }
                    try {
                        return getMovieDataFromJson(fetchtMovieJsonStr);
                    } catch (JSONException e) {
                        Log.e(LOG_TAG, e.getMessage(), e);
                        e.printStackTrace();
                    }

                    // This will only happen if there was an error getting or parsing the forecast.
                    return null;
                }
            }

            protected void onPostExecute(List<ReviewItem> ReviewItem) {

                if (ReviewItem != null) {

                    mReviewAdapter.clear();
                    mReviewAdapter.addAll(ReviewItem);
                    mReviewAdapter.notifyDataSetChanged();
                }
               /* if (result != null && mReviewAdapter != null) {
                    mReviewAdapter.clear();
                    MovieReview=result;
                    for (String dayForecastStr : result) {
                        mReviewAdapter.add(dayForecastStr);
                    }
                    // New data is back from the server.  Hooray!
                }*/
            }
        }


    }

}
