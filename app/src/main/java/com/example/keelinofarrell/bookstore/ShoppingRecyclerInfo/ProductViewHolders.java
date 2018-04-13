package com.example.keelinofarrell.bookstore.ShoppingRecyclerInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keelinofarrell.bookstore.BookSingleActivity;
import com.example.keelinofarrell.bookstore.CartActivity;
import com.example.keelinofarrell.bookstore.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by keelin.ofarrell on 13/04/2018.
 */

public class ProductViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView mBookImage;
    public TextView mTitle, mAuthor, mPrice, mBookId, mBookImageName;
    String bookId, userId;
    private FirebaseAuth mAuth;



    public ProductViewHolders(View itemView){
        super(itemView);
        itemView.setClickable(false);
        itemView.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mBookImage = (ImageView) itemView.findViewById(R.id.bookImage2);
        mTitle = (TextView) itemView.findViewById(R.id.bookTitle2);
        mAuthor = (TextView) itemView.findViewById(R.id.bookAuthor2);
        mPrice = (TextView) itemView.findViewById(R.id.bookPrice2);
        mBookId = (TextView) itemView.findViewById(R.id.bookID2);
        mBookImageName = (TextView) itemView.findViewById(R.id.profileImageText2);

        bookId = mBookId.getText().toString();


    }



    @Override
    public void onClick(final View view) {

        Intent intent = new Intent(view.getContext(), CartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("bookId", ((TextView)view.findViewById(R.id.bookId)).getText().toString() );
        intent.putExtras(bundle);
        view.getContext().startActivity(intent);

    }
}
