package com.example.android.mymovie.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.android.mymovie.app.data.MovieContract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DataFormatException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */

//http://inthecheesefactory.com/blog/retrofit-2.0/en
public class FetchMovieFragment extends Fragment {

    //Custome Image Adapter
    ImageAdapter mMovieAdapter;

    public static String prefparam;
    public  ArrayList<MovieItem> MovieURL = new ArrayList();
 //   public static ArrayList<MovieData> MovieParcable = new ArrayList();
    private GridView mMoviesGrid;
    int mPosition;
    boolean mDualPane;
    int mCurCheckPosition = 0;
  //  private static final String MOVIE_KEY ="keymovie" ;



    public FetchMovieFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


// Add this line in order for this fragment to handle menu events.
        View detailsFrame = getActivity().findViewById(R.id.movie_detail_container);
        if (detailsFrame!=null)
        {
            mDualPane = true;
        }
       else {
            mDualPane = false;
        }

        if (savedInstanceState==null || !savedInstanceState.containsKey(getString(R.string.MOVIE_KEY)))
        {
            MovieURL =new ArrayList();
        }
        else
        {
           /* Toast.makeText(getContext(),
                    " i am here ", Toast.LENGTH_LONG).show();*/

           MovieURL =  savedInstanceState.getParcelableArrayList(getString(R.string.MOVIE_KEY));
        }
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);



    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(getString(R.string.MOVIE_KEY), MovieURL);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // inflater.inflate(R.menu.fetchmoviefragment, menu);
    }

    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(int posttion);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_refresh) {

            updateMovie();
           *//*FetchMovieTask movieTask = new FetchMovieTask();
            movieTask.execute("popularity.desc");
            return true;*//*
        }*/

        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_main, container, false);





//call the adapter for constructor


        mMovieAdapter = new ImageAdapter(this.getActivity(), MovieURL);

        GridView mMoviesGrid = (GridView) rootView.findViewById(R.id.movieGrid);
        //Log.v("movieList size is", String.valueOf(movieList.size()));

        mMoviesGrid.setAdapter(mMovieAdapter);




        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (mDualPane) {
            // In dual-pane mode, the list view highlights the selected item.
            mMoviesGrid.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            showDetails(0);
        }





        mMoviesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  Intent intent = new Intent(getActivity(), DetailActivity.class);
                showDetails(position);






            }
        });

        return rootView;
    }



    void showDetails(int index) {
        MovieItem movie = (MovieItem) mMovieAdapter.getItem(index);
        Bundle extras = new Bundle();
        extras.putString("Movieposter", movie.getmMoviePoster());
        extras.putString("Title", movie.getMovieTitle());
        extras.putString("VoteAverage", movie.getmMovieVoteAverage());
        extras.putString("Overview", movie.getmMovieOverView());
        extras.putString("ReleaseDate", movie.getmMovieReleaseDate());
        extras.putString("PrefParm", prefparam);
        extras.putString("MovieID", movie.getmMovieId());

        Intent intent = new Intent(getActivity(), DetailActivity.class)
                .putExtras(extras);


        //if this is tablet view, take over that frame/fragment. dont take whole window

        DetailActivity.DetailFragment displayFrag = (DetailActivity.DetailFragment) getFragmentManager()
                .findFragmentById(R.id.movie_detail_container);

        if (displayFrag == null) {
            Log.e("v==", "null");
            startActivity(intent);
        } else {
            Log.e("v==", "not null");
            // fragment replacement stuff
            DetailActivity.DetailFragment fragment = new DetailActivity.DetailFragment();
            fragment.setArguments(extras);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, "frag tag")
                    .commit();

        }
    }
    private void updateMovie() {
        FetchMovieTask movieTask = new FetchMovieTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortorder = prefs.getString(getString(R.string.pref_sortorder_key),
                getString(R.string.pref_sortorder_mostpopular));

        movieTask.execute(sortorder);

        //  movieTask.execute("popularity.desc");
    }
    //start the activity
    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
        // FetchMovieTask movieTask = new FetchMovieTask();
        // movieTask.execute("popularity.desc");


    }









    public class FetchMovieTask extends AsyncTask<String, Void, List<MovieItem>> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        //Take the String representing the complete forecast in JSON Format and
        // pull out the data we need to construct the Strings needed for the wireframes.

        // Fortunately parsing is easy:  constructor takes the JSON string and converts it
        //into an Object hierarchy for us.


        private  List<MovieItem> getMovieDataFromJson(String fetchtMovieJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.

            final String TMB_RESULTS = "results";
            final String TMB_POSTER = "poster_path";
            final String TMB_TITLE = "title";
            final String TMB_VOTE_AVG = "vote_average";
            final String TMB_OVERVIW = "overview";
            final String TMB_RELEASEDATE="release_date";
            final String TMB_ID="id";
            JSONObject fetchtMovieJson = new JSONObject(fetchtMovieJsonStr);
            JSONArray movieArray = fetchtMovieJson.getJSONArray(TMB_RESULTS);

            int numMovies = movieArray.length();
            String[] resultStrs = new String[numMovies];
          List<MovieItem> moviesList= new ArrayList<>();
            for(int i = 0; i < movieArray.length(); i++) {

// don't need some of these yet
                //String results;
                String poster;
             //   String title;
               // String average;

// Get the JSON object representing the movie
                JSONObject movieDetail = movieArray.getJSONObject(i);

                poster = movieDetail.getString(TMB_POSTER);
             //   title = movieDetail.getString(TMB_TITLE);
                //average = movieDetail.getString(TMB_VOTE_AVG);

                //only need to return the poster for now
                //but it doesn't hurt to have the other code already
               // resultStrs[i] = "http://image.tmdb.org/t/p/w185"+poster;
                //Set movie item
              //  Bundle bundle ;

            //  MovieItem movieItem;//= new MovieItem();
               // movieItem.
               // moviesList.add()
            //  moviesList.add(new MovieItem(movieDetail.getString(TMB_POSTER));
             //  MovieItem[] movieItem={new MovieItem }

                moviesList.add(new MovieItem (movieDetail.getString(TMB_POSTER),
                        movieDetail.getString(TMB_TITLE),
                        movieDetail.getString(TMB_POSTER),
                        movieDetail.getString(TMB_OVERVIW),
                        movieDetail.getString(TMB_VOTE_AVG),
                        movieDetail.getString(TMB_RELEASEDATE),
                        movieDetail.getString(TMB_ID)));
              //  MovieURL. add(new MovieItem(movieDetail.getString(TMB_POSTER));

               /* MovieURL. add(new MovieItem(movieDetail.getString(TMB_POSTER)),
                        movieDetail.getString(TMB_TITLE),
                        movieDetail.getString(TMB_POSTER),
                        movieDetail.getString(TMB_OVERVIW),
                        movieDetail.getString(TMB_VOTE_AVG),
                        movieDetail.getString(TMB_RELEASEDATE),
                        movieDetail.getString(TMB_ID)));*/
              // movieItem.setMovieImageurl(movieDetail.getString(TMB_POSTER));
              //  movieItem.setMovieTitle(movieDetail.getString(TMB_TITLE));
              //  movieItem.setmMovieOverView(movieDetail.getString(TMB_OVERVIW));
              //  movieItem.setmMovieVoteAverage(movieDetail.getString(TMB_VOTE_AVG));
                //movieItem.setmMovieReleaseDate(movieDetail.getString(TMB_RELEASEDATE));
              //  movieItem.setmMovieId(movieDetail.getString(TMB_ID));


              //  InputStream input = connection.getInputStream();
              //  Bitmap myBitmap = BitmapFactory.decodeStream(input);
              Log.v(LOG_TAG,(movieDetail.getString(TMB_ID).toString()));
                  //     moviesList.add(movieItem);
              //  MovieData movie2=new MovieData(movies) ;

                //movieList.add(poster);
            }

            return moviesList;
        }
        private  List<MovieItem> getMovieDataFromDB()

            throws DataFormatException {

            List<MovieItem> moviesList = new ArrayList<>();
            String MovieImageurl;
             String movieTitle;
             String mMoviePoster;
             String mMovieOverView;
             String mMovieVoteAverage;
             String mMovieReleaseDate;
             String mMovieId;
            Cursor cursor = getContext().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,null,null,null,null,null);
            if (!cursor.moveToFirst()) {
                Log.v(LOG_TAG, "Favorite no found yet!");
                // Toast.makeText(getContext(), "Favorite no found yet!", Toast.LENGTH_LONG).show();

            } else {

                do {
                  //  MovieItem movieItem = new MovieItem((Parcel.obtain()));

                    MovieImageurl=   cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER));
                    movieTitle=   cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE));
                    mMoviePoster=   cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER));
                    mMovieOverView=   cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW));
                    mMovieVoteAverage= cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_RATING));
                    mMovieReleaseDate= cursor.getString( cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_DATE));
                    mMovieId=   cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));

                    moviesList.add(new MovieItem(MovieImageurl,movieTitle,mMoviePoster,mMovieOverView,mMovieVoteAverage,mMovieReleaseDate,
                            mMovieId ) );


                          /*  movieItem.setMovieTitle(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE)));
                    movieItem.setmMovieOverView(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW)));
                    movieItem.setmMovieVoteAverage(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_RATING)));
                    movieItem.setmMovieReleaseDate(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_DATE)));
                    movieItem.setmMovieId(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID)));*/

                   // moviesList.add(movieItem);


                } while (cursor.moveToNext());

                //   Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();

            }

            return moviesList;
        }

        @Override
        protected List<MovieItem> doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.


            // If there's no sort order or highest rating there's nothing to look up.  Verify size of params.


            if (params.length == 0) {
                return null;
            }

            prefparam=params[0];
            if ("favorites".equals(params[0])) {
                prefparam=params[0];
                Log.v(LOG_TAG, params[0]);
                try {
                    return getMovieDataFromDB();
                } catch (DataFormatException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    e.printStackTrace();
                }

                return null;
            }
            else
            {

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

                    final String FETCHMOVIE_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
                    final String QUERY_PARAM = "sort_by";
                    final String API_KEY = "api_key";
                    //


                    // Create the request to MovieDetails, and open the connection
                    Uri builtUri = Uri.parse(FETCHMOVIE_BASE_URL).buildUpon()
                            .appendQueryParameter(QUERY_PARAM, params[0])
                            .appendQueryParameter(API_KEY, apikey).build();
                    URL url = new URL(builtUri.toString());
                    //  Log.v(LOG_TAG, "Built URI " + builtUri.toString());

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

        @Override
        protected void onPostExecute(List<MovieItem> MovieList) {

            super.onPostExecute(MovieList);
            //clear movie list and adapter
            if (MovieList != null) {
               // MovieURL.clear();
                mMovieAdapter.clear();





                    mMovieAdapter.addAll(MovieList);
                mMovieAdapter.notifyDataSetChanged();

//                if (mPosition == ListView.INVALID_POSITION && MovieList != null && !MovieList.isEmpty()) {
//                    // select first item (tablet/phone UI distinction cannot be made here, let MainActivity decide)
//                    mPosition = 0;
//                    ((Callback) getActivity()).onItemSelected(MovieList.get(0));
//                } else {
//                    mMoviesGrid .smoothScrollToPositionFromTop(mPosition, 0);
//                }
              //  }


                // New data is back from the server.  Hooray!
            }
        }
    }
}

