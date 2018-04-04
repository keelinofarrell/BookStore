package com.example.keelinofarrell.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.keelinofarrell.bookstore.BookRecyclerInfo.BookAdapter;
import com.example.keelinofarrell.bookstore.BookRecyclerInfo.BookObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminBookActivity extends AppCompatActivity {

    private Button mAddBook, mCustomers, mLogout;
    private ListView mBooks;
    private RecyclerView mBookRecyclerView;
    private BookAdapter mBookAdapter;
    private RecyclerView.LayoutManager mBookLayoutManager;
    DatabaseReference mBooksDbRef;
    private EditText mSearch;
    private ArrayList<BookObject> mBooks1;
    private boolean isLoggingOut = false;
    String bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_book);

        mAddBook = (Button)findViewById(R.id.addbook);
        mCustomers = (Button)findViewById(R.id.viewCustomers);
        mLogout = (Button)findViewById(R.id.logout);
        mBookRecyclerView = (RecyclerView)findViewById(R.id.books);
        mBookRecyclerView.setNestedScrollingEnabled(true);
        mBookRecyclerView.setHasFixedSize(true);

        mBookLayoutManager = new LinearLayoutManager(AdminBookActivity.this);
        mBookRecyclerView.setLayoutManager(mBookLayoutManager);
        mBookAdapter = new BookAdapter(getDataSetHistory(), AdminBookActivity.this);
        mBookRecyclerView.setAdapter(mBookAdapter);


        mSearch = (EditText)findViewById(R.id.search);
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLoggingOut = true;
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AdminBookActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminBookActivity.this, ViewCustomers.class);
                startActivity(intent);
                finish();
                return;
            }
        });



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


    private void filter(String s) {

        List<BookObject> temp = new ArrayList<>();
        for(BookObject book : booksList){
            if(book.getTitle().toLowerCase().contains(s)){
                temp.add(book);
            }
        }
        mBookAdapter.updateList(temp);
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
                    booksList.add(book);
                    mBookAdapter.notifyDataSetChanged();
                    System.out.println(resultBooks);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private ArrayList<BookObject> booksList = new ArrayList<>();


    private ArrayList resultBooks = new ArrayList<BookObject>();

    private ArrayList<BookObject> getDataSetHistory() {
        return resultBooks;
    }


}
