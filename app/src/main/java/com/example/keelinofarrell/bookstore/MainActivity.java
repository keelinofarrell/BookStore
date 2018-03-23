package com.example.keelinofarrell.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mAdmin, mCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdmin = (Button)findViewById(R.id.admin);
        mCustomer = (Button)findViewById(R.id.customer);

        mAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AdminLogin.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CustomerLogin.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}
