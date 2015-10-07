package com.example.android.mymovie.app;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kamini on 9/14/2015.
 */
public class MovieItem implements Parcelable {
    private String MovieImageurl;
    private String movieTitle;
    private Bitmap mMoviePoster;
    private String mMovieOverView;
    private String mMovieVoteAverage;
    private String mMovieReleaseDate;
    private String mMovieId;
    protected MovieItem(Parcel in) {
        MovieImageurl = in.readString();
        movieTitle = in.readString();
        mMoviePoster = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
        mMovieOverView = in.readString();
        mMovieVoteAverage = in.readString();
        mMovieReleaseDate = in.readString();
        mMovieId = in.readString();
    }

    public String getmMovieId() {
        return mMovieId;
    }

    public void setmMovieId(String mMovieId) {
        this.mMovieId = mMovieId;
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

    public String getMovieImageurl() {
        return MovieImageurl;
    }

    public void setMovieImageurl(String movieImageurl) {
        MovieImageurl = movieImageurl;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(MovieImageurl);
        dest.writeString(movieTitle);
        dest.writeValue(mMoviePoster);
        dest.writeString(mMovieOverView);
        dest.writeString(mMovieVoteAverage);
        dest.writeString(mMovieReleaseDate);
        dest.writeString(mMovieId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}