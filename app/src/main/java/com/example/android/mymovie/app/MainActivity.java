package com.example.android.mymovie.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity implements FetchMovieFragment.Callback{
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);




        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Pop Movies");
   String titlename=(prefs.getString(getString(R.string.pref_sortorder_key),
           getString(R.string.pref_sortorder_mostpopular)));

        if ("popularity.desc".equals(titlename))
        {
            actionBar.setSubtitle(R.string.pref_sortorder_label_popularity);
        }else if ("vote_average.desc".equals(titlename))
        {
            actionBar.setSubtitle(R.string.pref_sortorder_label_highestvote);

        }else if("favorites".equals(titlename))
        {
            actionBar.setSubtitle(R.string.pref_sortorder_label_favorites);
        } else if ("upcoming".equals(titlename))
        {
            actionBar.setSubtitle(R.string.pref_sortorder_label_upcoming);
        }


        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()


                        .replace(R.id.movie_detail_container, new DetailActivity.DetailFragment(), DETAILFRAGMENT_TAG)
                .commit();




            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);


        }
//pass mtwopane for check in fragment
         FetchMovieFragment fetchmoviefragment =  ((FetchMovieFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_fetchmovie));
        fetchmoviefragment.setIfTwoPane(mTwoPane);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Bundle extras) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
           args.putParcelable(DetailActivity.DetailFragment.DETAIL_URI,extras);

            DetailActivity.DetailFragment fragment = new DetailActivity.DetailFragment();
            fragment.setArguments(extras);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {


            Intent intent = new Intent(this, DetailActivity.class)

                    .putExtras(extras);

            startActivity(intent);
        }
    }





}
