package com.example.keelinofarrell.bookstore.BookRecyclerInfo;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keelinofarrell.bookstore.BookSingleActivity;
import com.example.keelinofarrell.bookstore.R;

import org.w3c.dom.Text;

/**
 * Created by keelin.ofarrell on 28/03/2018.
 */

public class BookViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView mBookImage;
    public TextView mTitle, mAuthor, mPrice, mBookId, mBookImageName;

    public BookViewHolders(View itemView){
        super(itemView);
        itemView.setOnClickListener(this);

        mBookImage = (ImageView) itemView.findViewById(R.id.bookImage1);
        mTitle = (TextView) itemView.findViewById(R.id.booktitle);
        mAuthor = (TextView) itemView.findViewById(R.id.bookauthor);
        mPrice = (TextView) itemView.findViewById(R.id.bookprice);
        mBookId = (TextView) itemView.findViewById(R.id.bookId);
        mBookImageName = (TextView) itemView.findViewById(R.id.profileImageText);

    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), BookSingleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("bookId", mBookId.getText().toString());
        intent.putExtras(bundle);
        view.getContext().startActivity(intent);
    }
}
