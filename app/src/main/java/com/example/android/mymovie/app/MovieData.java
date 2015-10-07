package com.example.android.mymovie.app;

/**
 * Created by Kamini on 9/15/2015.
 */
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MovieData implements Parcelable {
List<MovieItem> movies = new ArrayList<>();
    MovieItem movie;

    protected MovieData(Parcel in) {
        if (in.readByte() == 0x01) {
            movies = new ArrayList<MovieItem>();
            in.readList(movies, MovieItem.class.getClassLoader());
        } else {
            movies = null;
        }
        movie = (MovieItem) in.readValue(MovieItem.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (movies == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(movies);
        }
        dest.writeValue(movie);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };
}