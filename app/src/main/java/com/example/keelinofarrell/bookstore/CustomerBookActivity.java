package com.example.keelinofarrell.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class CustomerBookActivity extends AppCompatActivity {

    private Button mProfile, mShopping, mLogout;
    private EditText mSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

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
    }
}
