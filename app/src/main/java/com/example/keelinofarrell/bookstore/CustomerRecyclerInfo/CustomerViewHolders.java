package com.example.keelinofarrell.bookstore.CustomerRecyclerInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keelinofarrell.bookstore.BookSingleActivity;
import com.example.keelinofarrell.bookstore.CustomerSingleActivity;
import com.example.keelinofarrell.bookstore.R;

import java.util.concurrent.TimeoutException;

/**
 * Created by keelin.ofarrell on 31/03/2018.
 */

public class CustomerViewHolders extends RecyclerView.ViewHolder implements OnClickListener {


    public ImageView mProfileImage;
    public TextView mFirstname, mLastname, mProfileImageName, mCustomerId, mEmail;

    public CustomerViewHolders(View itemView){
        super(itemView);
        itemView.setOnClickListener(this);

        mProfileImage = (ImageView)itemView.findViewById(R.id.Image1);
        mFirstname = (TextView)itemView.findViewById(R.id.firstname1);
        mLastname = (TextView)itemView.findViewById(R.id.lastname1);
        mCustomerId = (TextView)itemView.findViewById(R.id.customerId1);
        mProfileImageName = (TextView)itemView.findViewById(R.id.profileImageText1);
        mEmail = (TextView)itemView.findViewById(R.id.email);

        mCustomerId.setVisibility(View.GONE);


    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), CustomerSingleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("customerId", mCustomerId.getText().toString());
        intent.putExtras(bundle);
        view.getContext().startActivity(intent);
    }
}
