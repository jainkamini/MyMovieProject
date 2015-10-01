package com.example.android.mymovie.app;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class DetailActivity extends ActionBarActivity {

    public static String mMovieId;
    private static ArrayAdapter<String> mTrailerAdapter;
    private static ArrayAdapter<String> mReviewAdapter;
    public static  String mMovieTitle;
    public static  String mMovieOverview;
    public static  String mMovieVoteAverage;
    public static   String mMovieReleaseDate;
    public static  String mMoviePoster;
    public static String[] MovieTrailer ;
    public static String[] MovieReview ;

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

            final View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            String LOG_TAG = DetailActivity.class.getSimpleName();
            Intent intent = getActivity().getIntent();


            if (intent != null && intent.hasExtra("Title")) {


                mMovieTitle = (String) intent.getStringExtra("Title");
                ((TextView) rootView.findViewById(R.id.movietitle_text)).setText(mMovieTitle);
            }
            if (intent != null && intent.hasExtra("Overview")) {
                mMovieOverview = (String) intent.getStringExtra("Overview");
                ((TextView) rootView.findViewById(R.id.movieoverview_text)).setText(mMovieOverview);
            }
            if (intent != null && intent.hasExtra("VoteAverage")) {
                 mMovieVoteAverage = (String) intent.getStringExtra("VoteAverage");
                ((TextView) rootView.findViewById(R.id.movievoteAverage_text)).setText(mMovieVoteAverage+"/10");
            }
            if (intent != null && intent.hasExtra("MovieID")) {
                mMovieId =(String) intent.getStringExtra("MovieID");
               // mMovieId=(int)mMovieIds;
                Log.v(LOG_TAG,  mMovieId);
            }

            if (intent != null && intent.hasExtra("ReleaseDate")) {
              //  Log.v(LOG_TAG, (String) intent.getStringExtra("ReleaseDate"));
                 mMovieReleaseDate = (String) intent.getStringExtra("ReleaseDate");



                    Log.v(LOG_TAG, (String) mMovieReleaseDate);


                        mMovieReleaseDate = mMovieReleaseDate.substring(0, 4);


                    ((TextView) rootView.findViewById(R.id.moviereleasedate_text)).setText(mMovieReleaseDate);


            }
            if (intent != null && intent.hasExtra("ImagePoster")) {
                 mMoviePoster = (String)intent.getStringExtra("ImagePoster");

              ImageView imageView=(ImageView) rootView.findViewById(R.id.movieposter_image);
                 Context context=this.getContext();
                imageView.setAdjustViewBounds(true);
                Picasso.with(context).load( "http://image.tmdb.org/t/p/w185"+mMoviePoster).into(imageView);


            }

            ListView listView = (ListView) rootView.findViewById(R.id.listview_trailer);
          //  View headerView = ((LayoutInflater)Activity.this.getSystemService(Activity.this.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header, null, false);
           // listView.addHeaderView(headerView)
          //  ViewGroup header = (ViewGroup)inflater.inflate(R.layout.trailer_header,listView, false);

           // listView.addHeaderView(header);
           // FetchTrailerTask TrailerTask= new FetchTrailerTask();
           // TrailerTask.execute(mMovieId);
            mTrailerAdapter =
                    new ArrayAdapter<String>(
                            getActivity(), // The current context (this activity)
                            R.layout.trailer_list, // The name of the layout ID.
                            R.id.list_trailer_text, // The ID of the textview to populate.
                            new ArrayList<String>());



            listView.setAdapter(mTrailerAdapter);
            mReviewAdapter =
                    new ArrayAdapter<String>(
                            getActivity(), // The current context (this activity)
                            R.layout.review_list, // The name of the layout ID.
                            R.id.list_review_text, // The ID of the textview to populate.
                            new ArrayList<String>());
            ListView listViewreview = (ListView) rootView.findViewById(R.id.listview_review);
            listViewreview.setAdapter(mReviewAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    String movietrailer = mTrailerAdapter.getItem(position);
                    TextView list_trailer_text = (TextView) rootView.findViewById(R.id.list_trailer_text);
                    //  String videoId = list_trailer_text.getText().toString();
                    //   Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+videoId));
                    //   intent.putExtra("VIDEO_ID", videoId);
                    //   startActivity(intent);*/
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + list_trailer_text.getText())));
                    /*Intent intent = new Intent(getActivity(), DetailActivity.class)
                            .putExtra(Intent.EXTRA_TEXT, forecast);
                    startActivity(intent);*/
                }
            });

            Button favourite_button = (Button) rootView.findViewById(R.id.favourite_button);

            favourite_button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

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
                    int i;

                    for (i=0 ;i<MovieTrailer.length;i++)
                    {
                        ContentValues TrailerValues = new ContentValues();
                        TrailerValues.put(MovieContract.TrailerEntry.COLUMN_MOVIEID_KEY,
                                mMovieId);
                        TrailerValues.put(MovieContract.TrailerEntry.COLUMN_MOVIE_KEY,
                                MovieTrailer[i]);

                        Uri uri1 = getContext().getContentResolver().insert(
                                MovieContract.TrailerEntry.CONTENT_URI, TrailerValues);

                    }

                    for (i=0 ;i<MovieReview.length;i++)
                    {
                        ContentValues ReviewValues = new ContentValues();
                        ReviewValues.put(MovieContract.ReviewEntry.COLUMN_MOVIEID_KEY,
                                mMovieId);
                        ReviewValues.put(MovieContract.ReviewEntry.COLUMN_MOVIE_AUTHOR,
                                MovieReview[i]);
                        ReviewValues.put(MovieContract.ReviewEntry.COLUMN_MOVIE_CONTENT,
                                MovieReview[i]);
                        Uri uri2 = getContext().getContentResolver().insert(
                                MovieContract.ReviewEntry.CONTENT_URI, ReviewValues);

                    }



                    Toast.makeText(getContext(),
                            uri.toString() + "Inserted ", Toast.LENGTH_LONG).show();
                }

            });


            Button testbtn = (Button) rootView.findViewById(R.id.test_button);

            testbtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    String URL = "content://com.example.android.mymovie.app/movie";
                    String URL1 = "content://com.example.android.mymovie.app/trailer";

                    Uri friends = Uri.parse(URL);

                    Uri trailer = Uri.parse(URL1);

                    Cursor c = getContext().getContentResolver().query(friends, null, null, null, "movie_id");
                    Cursor c1 = getContext().getContentResolver().query(trailer, null, null, null, "movie_id");




                    String result = "Javacodegeeks Results:";
                    String result1 = "Javacodegeeks Results:";

                    if (!c.moveToFirst()) {

                        Toast.makeText(getContext(), result+" no content yet!", Toast.LENGTH_LONG).show();

                    }else{

                        do{

                            result = result + "\n" + c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID))+","+
                                    c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER))      ;



                        } while (c.moveToNext());

                     //   Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();

                    }
                    if (!c1.moveToFirst()) {

                        Toast.makeText(getContext(), result1+" no content yet!", Toast.LENGTH_LONG).show();

                    }else{

                        do{

                            result1 = result1 + "\n" + c1.getString(c.getColumnIndex(MovieContract.TrailerEntry.COLUMN_MOVIEID_KEY))+","+
                                    c1.getString(c1.getColumnIndex(MovieContract.TrailerEntry.COLUMN_MOVIE_KEY))      ;



                        } while (c1.moveToNext());

                        Toast.makeText(getContext(),result +"--"+ result1, Toast.LENGTH_LONG).show();

                    }





                }

            });
            return rootView;
        }

        public void onStart() {
            super.onStart();
          //  updateMovie();
           FetchTrailerTask TrailerTask= new FetchTrailerTask();
           TrailerTask.execute(mMovieId);
            FetchReviewTask ReviewTask= new FetchReviewTask();
            ReviewTask.execute(mMovieId);


        }



        public class FetchTrailerTask extends AsyncTask<String, Void,  String[]> {

            private final String LOG_TAG = FetchTrailerTask.class.getSimpleName();

            //Take the String representing the complete forecast in JSON Format and
            // pull out the data we need to construct the Strings needed for the wireframes.

            // Fortunately parsing is easy:  constructor takes the JSON string and converts it
            //into an Object hierarchy for us.

            private  String[] getMovieDataFromJson(String fetchtMovieJsonStr)
                    throws JSONException {

                // These are the names of the JSON objects that need to be extracted.
                final String TMB_RESULTS = "results";
                final String TMB_KEY = "key";

                JSONObject fetchtMovieJson = new JSONObject(fetchtMovieJsonStr);
                JSONArray movieArray = fetchtMovieJson.getJSONArray(TMB_RESULTS);

                int numMovies = movieArray.length();
                String[] resultStrs = new String[numMovies];
              //  Log.v(LOG_TAG,resultStrs.toString());
                for (int i = 0; i < movieArray.length(); i++) {





                    JSONObject movieDetail = movieArray.getJSONObject(i);


                    resultStrs[i] =  movieDetail.getString(TMB_KEY);;

                     // Log.v(LOG_TAG, movieDetail.getString(TMB_KEY).toString());

                }
                for (String s : resultStrs) {
                    Log.v(LOG_TAG, "Movie Trailer: " + s);
                }

                return resultStrs;
            }


            @Override
            protected  String[] doInBackground(String... params) {
                // These two need to be declared outside the try/catch
                // so that they can be closed in the finally block.


                // If there's no sort order or highest rating there's nothing to look up.  Verify size of params.
                if (params.length == 0) {
                    return null;
                }
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


            protected void onPostExecute(String[] result) {
                if (result != null && mTrailerAdapter != null) {

                    mTrailerAdapter.clear();
                    MovieTrailer  =result;
                    for (String TrailerStr : result) {
                        mTrailerAdapter.add(TrailerStr);

                    }
                    // New data is back from the server.  Hooray!
                }
            }
        }

        public class FetchReviewTask extends AsyncTask<String, Void,  String[]> {

            private final String LOG_TAG = FetchReviewTask.class.getSimpleName();

            //Take the String representing the complete forecast in JSON Format and
            // pull out the data we need to construct the Strings needed for the wireframes.

            // Fortunately parsing is easy:  constructor takes the JSON string and converts it
            //into an Object hierarchy for us.

            private  String[] getMovieDataFromJson(String fetchtMovieJsonStr)
                    throws JSONException {

                // These are the names of the JSON objects that need to be extracted.
                final String TMB_RESULTS = "results";
                final String TMB_AUTHOR = "author";
                final String TMB_CONTENT = "content";

                JSONObject fetchtMovieJson = new JSONObject(fetchtMovieJsonStr);
                JSONArray movieArray = fetchtMovieJson.getJSONArray(TMB_RESULTS);

                int numMovies = movieArray.length();
                String[] resultStrs = new String[numMovies];
                //  Log.v(LOG_TAG,resultStrs.toString());
                for (int i = 0; i < movieArray.length(); i++) {





                    JSONObject movieDetail = movieArray.getJSONObject(i);


                    resultStrs[i] =  movieDetail.getString(TMB_CONTENT) +" - "+movieDetail.getString(TMB_AUTHOR) ;

                    // Log.v(LOG_TAG, movieDetail.getString(TMB_KEY).toString());

                }
                for (String s : resultStrs) {
                    Log.v(LOG_TAG, "Movie Reviews: " + s);
                }

                return resultStrs;
            }


            @Override
            protected  String[] doInBackground(String... params) {
                // These two need to be declared outside the try/catch
                // so that they can be closed in the finally block.


                // If there's no sort order or highest rating there's nothing to look up.  Verify size of params.
                if (params.length == 0) {
                    return null;
                }
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


            protected void onPostExecute(String[] result) {
                if (result != null && mReviewAdapter != null) {
                    mReviewAdapter.clear();
                    MovieReview=result;
                    for (String dayForecastStr : result) {
                        mReviewAdapter.add(dayForecastStr);
                    }
                    // New data is back from the server.  Hooray!
                }
            }
        }


    }
}
