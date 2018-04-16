package com.example.keelinofarrell.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class CustomerSingleActivity extends AppCompatActivity {

    private TextView mFirstname, mLastName, mAddress;
    private Button mBack;
    private String customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_single);


        mFirstname = (TextView)findViewById(R.id.firstname);
        mLastName = (TextView)findViewById(R.id.lastname);
        mAddress = (TextView)findViewById(R.id.address);
        mBack = (Button)findViewById(R.id.back);


        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerSingleActivity.this, ViewCustomers.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            customerId = extras.getString("customerId");
        }

        getUserInfo();
    }

    private void getUserInfo() {
        DatabaseReference customerRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(customerId);
        customerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String customerId = dataSnapshot.getKey();
                    String firstname = "";
                    String lastname = "";
                    String address = "";
                    String profileImage = "";
                        if(dataSnapshot.child("firstname").getValue() != null){
                            firstname = dataSnapshot.child("firstname").getValue().toString();
                            mFirstname.setText(firstname);
                        }
                        if(dataSnapshot.child("lastname").getValue() != null){
                            lastname = dataSnapshot.child("lastname").getValue().toString();
                            mLastName.setText(lastname);
                        }
                        if(dataSnapshot.child("address").getValue() != null){
                            address = dataSnapshot.child("address").getValue().toString();
                            mAddress.setText(address);
                        }

                    }
                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
