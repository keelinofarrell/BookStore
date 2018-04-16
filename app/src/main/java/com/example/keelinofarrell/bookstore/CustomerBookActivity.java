package com.example.keelinofarrell.bookstore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.keelinofarrell.bookstore.BookRecyclerInfo.BookAdapter;
import com.example.keelinofarrell.bookstore.BookRecyclerInfo.BookObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CustomerBookActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Comparable {

    private Button mProfile, mShopping, mLogout;
    private EditText mSearch;
    private RecyclerView mBookRecyclerView;
    private BookAdapter mBookAdapter;
    private RecyclerView.LayoutManager mBookLayoutManager;
    DividerItemDecoration dividerItemDecoration;
    Spinner spinner;
    BookObject book;
    private static final String[]paths = {"ascending" , "descending"};
    String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        mBookRecyclerView = (RecyclerView)findViewById(R.id.books);
        mBookRecyclerView.setNestedScrollingEnabled(true);
        mBookRecyclerView.setHasFixedSize(true);

        mBookLayoutManager = new LinearLayoutManager(CustomerBookActivity.this);
        mBookRecyclerView.setLayoutManager(mBookLayoutManager);
        mBookAdapter = new BookAdapter(getDataSetHistory(), CustomerBookActivity.this);
        mBookRecyclerView.setAdapter(mBookAdapter);

        dividerItemDecoration = new DividerItemDecoration(mBookRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
        mBookRecyclerView.addItemDecoration(dividerItemDecoration);

        getBookIds();


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


        mLogout =(Button) findViewById(R.id.logout);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(CustomerBookActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mProfile = (Button)findViewById(R.id.profile);
        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerBookActivity.this, CustomerProfile.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mShopping = (Button)findViewById(R.id.shoppingCart);
        mShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerBookActivity.this, CartActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(CustomerBookActivity.this, android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                Collections.sort(resultBooks, new Comparator<BookObject>() {
                    @Override
                    public int compare(BookObject bookObject, BookObject t1) {
                        return bookObject.getTitle().compareToIgnoreCase(t1.getTitle());
                    }
                    @Override
                    public Comparator<BookObject> reversed() {
                        return null;
                    }
                });
                mBookAdapter.notifyDataSetChanged();
                break;
            case 1:
                Collections.reverse(resultBooks);break;
        }
        mBookAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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

    private void filter(String s) {

        List<BookObject> temp = new ArrayList<>();
        for(BookObject book : booksList){
            if(book.getTitle().toLowerCase().contains(s)){
                temp.add(book);
            }
            if(book.getAuthor().toLowerCase().contains(s)){
                temp.add(book);
            }
        }
        mBookAdapter.updateList(temp);
    }

    private void getBookInfo(String key) {

        DatabaseReference booksDB = FirebaseDatabase.getInstance().getReference().child("Books").child(key);
        booksDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String bookId = dataSnapshot.getKey().toString();
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

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
