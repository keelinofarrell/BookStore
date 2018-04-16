package com.example.keelinofarrell.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class BookReview extends AppCompatActivity {

    String bookId;
    TextView mRating;
    Button mConfirm;
    RatingBar mRatingBar;
    EditText mComment;
    String rating, comment, bookTitle, userId;
    FirebaseAuth mAuth;

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

        getRatingIds();



    }

    private void getRatingIds() {

        DatabaseReference ratings = FirebaseDatabase.getInstance().getReference().child("Books").child(bookId).child("rating");
    }

    private void saveRatingtoBook(final String bookId) {

        DatabaseReference bookref = FirebaseDatabase.getInstance().getReference().child("Books").child(bookId);
        bookref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    bookTitle = dataSnapshot.child("Title").getValue().toString();

                    mRating.setText("Rate the book: " + bookTitle);

                    DatabaseReference ratingsRef = FirebaseDatabase.getInstance().getReference().child("Books").child(bookId).child("rating");

                    rating = String.valueOf(mRatingBar.getRating());
                    comment = mComment.getText().toString();

                    HashMap ratings = new HashMap();
                    ratings.put("customerId", userId);
                    ratings.put("rating", rating);
                    ratings.put("comment", comment);

                    ratingsRef.push().setValue(ratings);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
