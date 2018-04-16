package com.example.keelinofarrell.bookstore.PurchasesRecyclerInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keelinofarrell.bookstore.BookSingleActivity;
import com.example.keelinofarrell.bookstore.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by keelin.ofarrell on 16/04/2018.
 */

public class PurchaseViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {


    public ImageView mBookImage;
    public TextView mTitle, mAuthor, mPrice, mBookId, mBookImageName, mDate;
    String bookId, userId;
    private FirebaseAuth mAuth;



    public PurchaseViewHolders(View itemView){
        super(itemView);
        itemView.setClickable(true);
        itemView.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mBookImage = (ImageView) itemView.findViewById(R.id.bookImage1);
        mTitle = (TextView) itemView.findViewById(R.id.booktitle);
        mAuthor = (TextView) itemView.findViewById(R.id.bookauthor);
        mPrice = (TextView) itemView.findViewById(R.id.bookprice);
        mBookId = (TextView) itemView.findViewById(R.id.bookId);
        mBookImageName = (TextView) itemView.findViewById(R.id.profileImageText);
        mDate = (TextView)itemView.findViewById(R.id.datepurchased);

        bookId = mBookId.getText().toString();


    }




    @Override
    public void onClick(View view) {



    }
}
