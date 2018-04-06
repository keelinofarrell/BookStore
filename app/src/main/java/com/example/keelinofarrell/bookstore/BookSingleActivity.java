package com.example.keelinofarrell.bookstore;

import android.content.Intent;
import android.media.Image;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class BookSingleActivity extends AppCompatActivity {

    private EditText mTitle, mISBN, mAuthor, mPrice, mStock, mCategory;
    private TextView mText;
    private ImageView mBookImage, mMainImage;
    private Button mConfirm, mBack;
    String bookId;
    String mTitle1, mAuthor1, mISBN1, mPrice1, mStock1, mCategory1, mProfileUrl;
    DatabaseReference booksinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_single);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            bookId = extras.getString("bookId");
        }

        booksinfo = FirebaseDatabase.getInstance().getReference().child("Books").child(bookId);

        getBookInfo();

        mText = (TextView)findViewById(R.id.bookdetails);
        mTitle = (EditText)findViewById(R.id.BookTitle);
        mAuthor = (EditText)findViewById(R.id.BookAuthor);
        mPrice = (EditText)findViewById(R.id.BookPrice);
        mISBN = (EditText)findViewById(R.id.BookISBN);
        mStock = (EditText)findViewById(R.id.BookQuantity);
        mMainImage = (ImageView)findViewById(R.id.backdrop);
        mBookImage = (ImageView)findViewById(R.id.bookCover);
        mPrice = (EditText)findViewById(R.id.BookPrice);
        mCategory = (EditText)findViewById(R.id.BookCategory);
        mConfirm = (Button)findViewById(R.id.change);
        mBack = (Button)findViewById(R.id.back);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookSingleActivity.this, AdminBookActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });



    }

    private void getBookInfo() {
        booksinfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){
                    for(DataSnapshot child : dataSnapshot.getChildren()){
                        if(child.getKey().equals("ISBN")){
                            mISBN.setText(child.getValue().toString());
                        }
                        if(child.getKey().equals("Title")){
                            mTitle.setText(child.getValue().toString());
                        }
                        if(child.getKey().equals("Author")){
                            mAuthor.setText(child.getValue().toString());
                        }
                        if(child.getKey().equals("Category")){
                            mCategory.setText(child.getValue().toString());
                        }
                        if(child.getKey().equals("Price")){
                            mPrice.setText(child.getValue().toString());
                        }
                        if(child.getKey().equals("Stock")){
                            mStock.setText(child.getValue().toString());
                        }
                        if(child.getKey().equals("profileImageURL")){
                            mProfileUrl = child.getValue().toString();
                            Glide.with(getApplication()).load(mProfileUrl).into(mBookImage);
                        }
                    }
                    /*Map<String, Object>map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("ISBN") != null){
                        mISBN1 = map.get("ISBN").toString();
                        mISBN.setText(mISBN1);
                    }
                    if(map.get("Title") != null){
                        mTitle1 = map.get("Title").toString();
                        mTitle.setText(mTitle1);
                    }
                    if(map.get("Author") != null){
                        mAuthor1 = map.get("Author").toString();
                        mAuthor.setText(mAuthor1);
                    }
                    if(map.get("Price") != null){
                        mPrice1 = map.get("Price").toString();
                        mPrice.setText(mPrice1);
                    }
                    if(map.get("Category") != null){
                        mCategory1 = map.get("Category").toString();
                        mCategory.setText(mCategory1);
                    }
                    if(map.get("Stock") != null){
                        mStock1 = map.get("Stock").toString();
                        mStock.setText(mStock1);
                    }
                    if(map.get("profileImageURL")!=null){
                        mProfileUrl = map.get("profileImageURL").toString();
                        Glide.with(getApplication()).load(mProfileUrl).into(mBookImage);
                    }*/
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
