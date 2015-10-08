package com.example.android.mymovie.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kamini on 10/5/2015.
 */
public class TrailerItem  implements Parcelable{
    private String mTrailerKey;
    private String mTrailerName;


    public  TrailerItem(String mTrailerKey,String mTrailerName){

        this.mTrailerKey=mTrailerKey;
        this.mTrailerName=mTrailerName;
    }
    public String getmTrailerKey() {
        return mTrailerKey;
    }

    public void setmTrailerKey(String mTrailerKey) {
        this.mTrailerKey = mTrailerKey;
    }

    public String getmTrailerName() {
        return mTrailerName;
    }

    public void setmTrailerName(String mTrailerName) {
        this.mTrailerName = mTrailerName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTrailerKey);
        dest.writeString(mTrailerName);

    }
    protected TrailerItem(Parcel in)
    {
        mTrailerKey = in.readString();
        mTrailerName = in.readString();
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TrailerItem> CREATOR = new Parcelable.Creator<TrailerItem>() {
        @Override
        public TrailerItem createFromParcel(Parcel in) {
            return new TrailerItem(in);
        }

        @Override
        public TrailerItem[] newArray(int size) {
            return new TrailerItem[size];
        }
    };
}
