package com.example.keelinofarrell.bookstore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.keelinofarrell.bookstore.BookRecyclerInfo.BookObject;
import com.example.keelinofarrell.bookstore.CustomerRecyclerInfo.CustomerAdapter;
import com.example.keelinofarrell.bookstore.CustomerRecyclerInfo.CustomerObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewCustomers extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CustomerAdapter mAdapter;
    private RecyclerView.LayoutManager mCustomerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customers);

        mRecyclerView = (RecyclerView)findViewById(R.id.customers);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setHasFixedSize(true);

        mCustomerLayout = new LinearLayoutManager(ViewCustomers.this);
        mRecyclerView.setLayoutManager(mCustomerLayout);
        mAdapter = new CustomerAdapter(getDataSetHistory(), ViewCustomers.this);
        mRecyclerView.setAdapter(mAdapter);

        getCustomerIds();

    }

    private void getCustomerIds() {
        DatabaseReference mCustomersRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers");
        mCustomersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot customers : dataSnapshot.getChildren()){
                        getCustomerInfo(customers.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getCustomerInfo(String key) {
        DatabaseReference custDB = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(key);
        custDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String customerId = dataSnapshot.getKey();
                    String firstname = "";
                    String lastname = "";
                    String email = "";
                    String profileImage = "";

                    if(dataSnapshot.child("firstname").getValue() != null){
                        firstname = dataSnapshot.child("firstname").getValue().toString();
                    }
                    if(dataSnapshot.child("lastname").getValue() != null){
                        lastname = dataSnapshot.child("lastname").getValue().toString();
                    }
                    if(dataSnapshot.child("email").getValue() != null){
                        email = dataSnapshot.child("email").getValue().toString();
                    }
                    if(dataSnapshot.child("profileImageUrl").getValue() != null){
                        profileImage = dataSnapshot.child("profileImageUrl").getValue().toString();
                    }

                    CustomerObject customer = new CustomerObject(customerId, firstname, lastname, email, profileImage);
                    resultCusts.add(customer);
                    customerList.add(customer);
                    mAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private ArrayList<CustomerObject> customerList = new ArrayList<>();


    private ArrayList resultCusts = new ArrayList<CustomerObject>();

    private ArrayList<CustomerObject> getDataSetHistory() {
        return resultCusts;
    }
}
