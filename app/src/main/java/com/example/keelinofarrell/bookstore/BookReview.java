package com.example.keelinofarrell.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.keelinofarrell.bookstore.CustomerRecyclerInfo.CustomerObject;
import com.example.keelinofarrell.bookstore.ReviewRecyclerInfo.ReviewAdapter;
import com.example.keelinofarrell.bookstore.ReviewRecyclerInfo.ReviewObject;
import com.example.keelinofarrell.bookstore.ShoppingRecyclerInfo.ProductAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class BookReview extends AppCompatActivity {

    String bookId;
    TextView mRating;
    Button mConfirm;
    RatingBar mRatingBar;
    EditText mComment;
    String rating, comment, bookTitle, userId, profileImageUrl, firstname, lastname, date;
    FirebaseAuth mAuth;
    Date currentTime;
    private RecyclerView mRecyclerView;
    private ReviewAdapter mAdapter;
    private RecyclerView.LayoutManager mReviewLayout;
    DividerItemDecoration dividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_review);

        mRatingBar = (RatingBar)findViewById(R.id.ratingBar);
        mComment = (EditText)findViewById(R.id.comment);
        mConfirm = (Button)findViewById(R.id.confirm);
        mRating = (TextView)findViewById(R.id.Rate);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mRecyclerView = (RecyclerView)findViewById(R.id.reviews);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setHasFixedSize(true);

        mReviewLayout = new LinearLayoutManager(BookReview.this);
        mRecyclerView.setLayoutManager(mReviewLayout);
        mAdapter = new ReviewAdapter(getDataSetHistory(), BookReview.this);
        mRecyclerView.setAdapter(mAdapter);

        dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);



        Bundle extras = getIntent().getExtras();
        if(extras != null){
            bookId = extras.getString("bookId");
        }

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRatingtoBook(bookId);
                Intent intent = new Intent(BookReview.this, CustomerBookActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        getUserInfo();

        getRatingIds();



    }

    private void getUserInfo() {

        DatabaseReference userinfo = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(userId);
        userinfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if(dataSnapshot.child("firstname").getValue().toString() != null){
                        firstname = dataSnapshot.child("firstname").getValue().toString();
                    }
                    if(dataSnapshot.child("lastname").getValue().toString() != null){
                        lastname = dataSnapshot.child("lastname").getValue().toString();
                    }
                    if(dataSnapshot.child("profileImageUrl").getValue() != null){
                        profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getRatingIds() {

        DatabaseReference ratings = FirebaseDatabase.getInstance().getReference().child("Books").child(bookId).child("rating");
        ratings.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot reviews : dataSnapshot.getChildren()){
                        getReviewInfo(reviews.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getReviewInfo(String key) {
        DatabaseReference reviewinfo = FirebaseDatabase.getInstance().getReference().child("Books").child(bookId).child("rating").child(key);
        reviewinfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    firstname = "";
                    lastname = "";
                    comment = "";
                    rating = "";
                    date = "";
                    profileImageUrl = "";
                    if(dataSnapshot.child("firstname").getValue().toString() != null){
                        firstname = dataSnapshot.child("firstname").getValue().toString();
                    }
                    if(dataSnapshot.child("lastname").getValue().toString() != null){
                        lastname = dataSnapshot.child("lastname").getValue().toString();
                    }
                    if(dataSnapshot.child("comment").getValue().toString() != null){
                        comment = dataSnapshot.child("comment").getValue().toString();
                    }
                    if(dataSnapshot.child("rating").getValue().toString() != null){
                        rating = dataSnapshot.child("rating").getValue().toString();
                    }
                    if(dataSnapshot.child("date").getValue().toString() != null){
                        date = dataSnapshot.child("date").getValue().toString();
                    }
                    if(dataSnapshot.child("profileUrl").getValue() != null){
                        profileImageUrl = dataSnapshot.child("profileUrl").getValue().toString();
                    }

                    ReviewObject reviewObject = new ReviewObject(date, comment, firstname, lastname, rating, profileImageUrl);
                    resultReview.add(reviewObject);
                    reviewList.add(reviewObject);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveRatingtoBook(final String bookId) {

        DatabaseReference bookref = FirebaseDatabase.getInstance().getReference().child("Books").child(bookId);
        bookref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    bookTitle = dataSnapshot.child("Title").getValue().toString();

                    mRating.setText("Rate the book: " + bookTitle);

                    Date todaysdate = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    date = format.format(todaysdate);
                    DatabaseReference ratingsRef = FirebaseDatabase.getInstance().getReference().child("Books").child(bookId).child("rating");

                    rating = String.valueOf(mRatingBar.getRating());
                    comment = mComment.getText().toString();

                    HashMap ratings = new HashMap();
                    ratings.put("customerId", userId);
                    ratings.put("rating", rating);
                    ratings.put("comment", comment);
                    ratings.put("date", date);
                    ratings.put("firstname", firstname);
                    ratings.put("lastname", lastname);
                    ratings.put("profileUrl", profileImageUrl);

                    ratingsRef.push().setValue(ratings);




                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private ArrayList<ReviewObject> reviewList = new ArrayList<>();


    private ArrayList resultReview = new ArrayList<ReviewObject>();

    private ArrayList<ReviewObject> getDataSetHistory() {
        return resultReview;
    }
}
