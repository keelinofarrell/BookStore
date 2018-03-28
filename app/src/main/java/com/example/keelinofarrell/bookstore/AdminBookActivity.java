package com.example.keelinofarrell.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class AdminBookActivity extends AppCompatActivity {

    private Button mAddBook, mProfile;
    private ListView mBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_book);

        mAddBook = (Button)findViewById(R.id.addbook);
        mProfile = (Button)findViewById(R.id.viewProfile);
        mBooks = (ListView)findViewById(R.id.books);

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
}
