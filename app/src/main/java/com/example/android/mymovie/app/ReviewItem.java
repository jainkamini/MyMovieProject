package com.example.android.mymovie.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kamini on 10/8/2015.
 */
public class ReviewItem implements Parcelable {

    private String mReviewAuthor;
    private String mReviewContent;
    public ReviewItem(String mReviewAuthor,String mReviewContent)
    {
        this.mReviewAuthor=mReviewAuthor;
        this.mReviewContent=mReviewContent;

    }

    public String getmReviewAuthor() {
        return mReviewAuthor;
    }

    public void setmReviewAuthor(String mReviewAuthor) {
        this.mReviewAuthor = mReviewAuthor;
    }

    public String getmReviewContent() {
        return mReviewContent;
    }

    public void setmReviewContent(String mReviewContent) {
        this.mReviewContent = mReviewContent;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mReviewAuthor);
        dest.writeString(mReviewContent);

    }
    protected ReviewItem(Parcel in)
    {
        mReviewAuthor = in.readString();
        mReviewContent = in.readString();
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ReviewItem> CREATOR = new Parcelable.Creator<ReviewItem>() {
        @Override
        public ReviewItem createFromParcel(Parcel in) {
            return new ReviewItem(in);
        }

        @Override
        public ReviewItem[] newArray(int size) {
            return new ReviewItem[size];
        }
    };
}
