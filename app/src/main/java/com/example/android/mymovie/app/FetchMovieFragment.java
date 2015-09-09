package com.example.android.mymovie.app;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class FetchMovieFragment extends Fragment {
    private ArrayAdapter<String> mPosterAdapter;

    public ArrayList<String> movieList = new ArrayList<String>();
   // private ArrayAdapter<String> mPosterAdapter;

  //  static ArrayList<String> movieList = new ArrayList<String>();

    private GridView mMoviesGrid;
    private ImageAdapter mMoviesAdapter;
   // ArrayList<Item> gridArray = new ArrayList<Item>();




    // private ArrayAdapter<String> mPosterAdapter;

   // static ArrayList<String> movieList = new ArrayList<String>();
    //public ArrayList<String> mPosterMoviePaths;
  //  static ArrayList<String> movieList = new ArrayList<String>();
    // String mMovieJsonStr = null;

    public FetchMovieFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
        // new FeatchMovieTask().execute();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fetchmoviefragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            FetchMovieTask movieTask = new FetchMovieTask();
            movieTask.execute("popularity.desc");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        // references to our images

      /* Integer[] mThumbIds ={
                R.drawable.download8, R.drawable.download7,
                R.drawable.download9, R.drawable.download1,

        };
*/
       GridView mMoviesGrid  = (GridView) rootView.findViewById(R.id.movieGrid);
      //  mMoviesGrid.setAdapter(new ImageAdapter(getContext(),mThumbIds));
     movieList.add("http://i.imgur.com/DvpvklR.png");
       // movieList.add("R.drawable.download1");
     //   List<String> movieLis1t = new ArrayList<String>(Arrays.asList(movieList));
        mPosterAdapter = new testclass(getContext(), movieList);
        Log.v("movieList size is", String.valueOf(movieList.size()));
        //GridView mMoviesGrid  = (GridView) rootView.findViewById(R.id.movieGrid);
        mMoviesGrid.setAdapter(mPosterAdapter);

       // Integer[] mThumbIds ={
         //      R.drawable.download7

        //};

      //  List<String> weekForecast = new ArrayList<String>(Arrays.asList(mThumbIds));
      //  GridView mMoviesGrid  = (GridView) rootView.findViewById(R.id.movieGrid);

       // testclass imageAdapter = new testclass(getActivity(),weekForecast);
     //   mMoviesGrid.setAdapter(new ImageAdapter(getContext(),mThumbIds));












        return rootView;
    }

    /*private void updateMovies(){
        FetchMovieTask movieInfo = new FetchMovieTask();
        movieInfo.execute();
    }*/

  /*  @Override
    public void onStart(){
        super.onStart();
        updateMovies();
    }*/








    public class FetchMovieTask extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();
        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private String[] getMovieDataFromJson(String fetchtMovieJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
       /* final String OWM_LIST = "list";
        final String OWM_WEATHER = "weather";
        final String OWM_TEMPERATURE = "temp";
        final String OWM_MAX = "max";
        final String OWM_MIN = "min";
        final String OWM_DESCRIPTION = "main";*/

            final String TMB_RESULTS = "results";
            final String TMB_POSTER = "poster_path";
            final String TMB_TITLE = "title";
            final String TMB_VOTE_AVG = "vote_average";
            final String TMB_OVERVIW = "overview";
            final String TMB_RELEASEDATE="relesedate";
            JSONObject fetchtMovieJson = new JSONObject(fetchtMovieJsonStr);
            JSONArray movieArray = fetchtMovieJson.getJSONArray(TMB_RESULTS);

            int numMovies = movieArray.length();
            String[] resultStrs = new String[numMovies];

            for(int i = 0; i < movieArray.length(); i++) {

// don't need some of these yet
                //String results;
                String poster;
                //String title;
                // String average;
// Get the JSON object representing the movie
                JSONObject movieDetail = movieArray.getJSONObject(i);

                poster = movieDetail.getString(TMB_POSTER);
                //title = movieDetail.getString(TMB_TITLE);
                //average = movieDetail.getString(TMB_VOTE_AVG);

                //only need to return the poster for now
                //but it doesn't hurt to have the other code already
                resultStrs[i] = poster;
                Log.v(LOG_TAG, poster.toString());
                //movieList.add(poster);
            }

            return resultStrs;
        }


        @Override
        protected String[] doInBackground(String... params) {
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
            String apikey = "xxxx";


            try {
                // Construct the URL for the Fetchmovie query
                // Possible parameters are avaiable at OWM's Fetchmovie API page, at
                // http://openweathermap.org/API#forecast

                final String FETCHMOVIE_BASE_URL ="http://api.themoviedb.org/3/discover/movie?";
                final String QUERY_PARAM = "sort_by";
                final    String API_KEY = "api_key";
                //



                // Create the request to OpenWeatherMap, and open the connection
                Uri builtUri = Uri.parse(FETCHMOVIE_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, params[0])
                        .appendQueryParameter(API_KEY, apikey). build();
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
                Log.v(LOG_TAG, "Fetchmovie string: " + fetchtMovieJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
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

        @Override
        protected void onPostExecute(String[] result) {
            mPosterAdapter.notifyDataSetChanged();
            mPosterAdapter = new testclass(getActivity(), movieList);
            if (result != null) {
                mPosterAdapter.clear();
                for (String resultStrs : result) {
                    mPosterAdapter.add(resultStrs);
                }
                mPosterAdapter.notifyDataSetChanged();

                // New data is back from the server.  Hooray!
            }
        }
    }
}


