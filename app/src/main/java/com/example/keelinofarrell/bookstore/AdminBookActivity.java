package com.example.keelinofarrell.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.keelinofarrell.bookstore.BookRecyclerInfo.BookAdapter;
import com.example.keelinofarrell.bookstore.BookRecyclerInfo.BookObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminBookActivity extends AppCompatActivity {

    private Button mAddBook, mProfile;
    private ListView mBooks;
    private RecyclerView mBookRecyclerView;
    private RecyclerView.Adapter mBookAdapter;
    private RecyclerView.LayoutManager mBookLayoutManager;
    DatabaseReference mBooksDbRef;
    String bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_book);

        mAddBook = (Button)findViewById(R.id.addbook);
        mProfile = (Button)findViewById(R.id.viewProfile);
        mBookRecyclerView = (RecyclerView)findViewById(R.id.books);
        mBookRecyclerView.setNestedScrollingEnabled(true);
        mBookRecyclerView.setHasFixedSize(true);

        mBookLayoutManager = new LinearLayoutManager(AdminBookActivity.this);
        mBookRecyclerView.setLayoutManager(mBookLayoutManager);
        mBookAdapter = new BookAdapter(getDataSetHistory(), AdminBookActivity.this);
        mBookRecyclerView.setAdapter(mBookAdapter);




        getBookIds();

        mAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminBookActivity.this, AddBookActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }

    private void getBookIds() {
        DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference().child("Books");
        booksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot books : dataSnapshot.getChildren()){
                        getBookInfo(books.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getBookInfo(String key) {

        DatabaseReference booksDB = FirebaseDatabase.getInstance().getReference().child("Books").child(key);
        booksDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String bookId = dataSnapshot.getKey();
                    String title = "";
                    String author = "";
                    String price = "";
                    String profileImageUrl= "";
                    if(dataSnapshot.child("Title").getValue() != null){
                        title = dataSnapshot.child("Title").getValue().toString();
                    }
                    if(dataSnapshot.child("Author").getValue() != null){
                        author = dataSnapshot.child("Author").getValue().toString();
                    }
                    if(dataSnapshot.child("Price").getValue() != null){
                        price = dataSnapshot.child("Price").getValue().toString();
                    }
                    if(dataSnapshot.child("profileImageURL").getValue() != null){
                        profileImageUrl = dataSnapshot.child("profileImageURL").getValue().toString();
                    }

                    BookObject book = new BookObject(bookId, title, author, price, profileImageUrl);
                    resultBooks.add(book);
                    mBookAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private ArrayList resultBooks = new ArrayList<BookObject>();

    private ArrayList<BookObject> getDataSetHistory() {
        return resultBooks;
    }
}
