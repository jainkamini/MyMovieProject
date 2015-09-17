package com.example.android.mymovie.app;

/**
 * Created by Kamini on 9/15/2015.
 */
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MovieData implements  Parcelable{
    private final String LOG_TAG = MovieData.class.getSimpleName();
  //  private List<MovieItem> movie;
   // int studId;
  List<MovieItem> movies = new ArrayList<>();
    MovieItem movie;

    public MovieData(MovieItem movie) {
        super();
        this.movie = movie;
       // this.studId = studId;
    }

    public MovieItem getMovie() {
        return movie;
    }

    public void setMovie(MovieItem movie) {
        this.movie = movie;
    }




    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
       // dest.writeString(item);
        dest.writeString(movie.getMovieImageurl());
        dest.writeString(movie.getMovieTitle());


      //  dest.writeInt(studId);

        Log.v(LOG_TAG,"MovieTitle :"+ movie.getMovieTitle());
    }

    private void readFromParcel(Parcel in) {

      //  movie = new ArrayList<MovieItem>();
       // Log.v(LOG_TAG,"MovieTitle for read :"+ in.readString());
        String movieurl=in.readString();
       String moveTitle=in.readString();
        Log.v(LOG_TAG, "MovieTitle for read :" + movieurl);
       // MovieItem movie=new MovieItem();
       // movie.setMovieImageurl(movieurl);

       //movie.setMovieTitle(moveTitle);
      //  movies.add(movie);
       //movie.setMovieImageurl(movieurl);
      //  Log.v(LOG_TAG, "MovieTitle for read :" + movie.getMovieTitle());


       // movie=new MovieItem(movieurl,moveTitle);

      //  item = in.r
      //  studId = in.readInt();
    }

    public MovieData(Parcel in){
        readFromParcel(in);
    }

    public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator<MovieData>() {

        @Override
        public MovieData createFromParcel(Parcel source) {
            return new MovieData(source);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

}
