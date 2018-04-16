package com.example.keelinofarrell.bookstore.ReviewRecyclerInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keelinofarrell.bookstore.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by keelin.ofarrell on 16/04/2018.
 */

public class ReviewViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public ImageView mBookImage;
    public TextView mBookImageName, mDate, mRating, mComment, mFirstname, mLastname;
    String bookId, userId;


    public ReviewViewHolders(View itemView){
        super(itemView);
        itemView.setClickable(false);

        mBookImage = (ImageView) itemView.findViewById(R.id.Image1);
        mFirstname = (TextView) itemView.findViewById(R.id.firstname1);
        mLastname= (TextView) itemView.findViewById(R.id.lastname1);
        mRating = (TextView) itemView.findViewById(R.id.rating);
        mComment = (TextView) itemView.findViewById(R.id.comment);
        mBookImageName = (TextView) itemView.findViewById(R.id.profileImageText1);
        mDate = (TextView)itemView.findViewById(R.id.date);

    }

    @Override
    public void onClick(View view) {

    }
}
