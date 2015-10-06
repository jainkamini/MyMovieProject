package com.example.android.mymovie.app;

import android.graphics.Bitmap;

/**
 * Created by Kamini on 9/14/2015.
 */
public class MovieItem {
    private String MovieImageurl;
    private String movieTitle;
    private Bitmap mMoviePoster;
    private String mMovieOverView;
    private String mMovieVoteAverage;
    private String mMovieReleaseDate;
    private String mMovieId;








    public MovieItem() {
        super();
          }
    public String getMovieImageurl() {
        return MovieImageurl;
    }

    public void setMovieImageurl(String movieImageurl) {
        MovieImageurl = movieImageurl;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getmMovieOverView() {
        return mMovieOverView;
    }

    public void setmMovieOverView(String mMovieOverView) {
        this.mMovieOverView = mMovieOverView;
    }

    public Bitmap getmMoviePoster() {
        return mMoviePoster;
    }

    public void setmMoviePoster(Bitmap mMoviePoster) {
        this.mMoviePoster = mMoviePoster;
    }

    public String getmMovieReleaseDate() {
        return mMovieReleaseDate;
    }

    public void setmMovieReleaseDate(String mMovieReleaseDate) {
        this.mMovieReleaseDate = mMovieReleaseDate;
    }

    public String getmMovieVoteAverage() {
        return mMovieVoteAverage;
    }

    public void setmMovieVoteAverage(String mMovieVoteAverage) {
        this.mMovieVoteAverage = mMovieVoteAverage;
    }

    public String getmMovieId() {
        return mMovieId;
    }

    public void setmMovieId(String mMovieId) {
        this.mMovieId = mMovieId;
    }




}
