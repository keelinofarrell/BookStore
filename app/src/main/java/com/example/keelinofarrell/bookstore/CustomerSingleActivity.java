package com.example.keelinofarrell.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.keelinofarrell.bookstore.PurchasesRecyclerInfo.PurchaseAdapter;
import com.example.keelinofarrell.bookstore.PurchasesRecyclerInfo.PurchaseObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class CustomerSingleActivity extends AppCompatActivity {

    private TextView mFirstname, mLastName, mAddress;
    private Button mBack;
    private String customerId, bookId, date;

    private RecyclerView mRecyclerView;
    private PurchaseAdapter mAdapter;
    private RecyclerView.LayoutManager mProductLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_single);


        mFirstname = (TextView)findViewById(R.id.firstname);
        mLastName = (TextView)findViewById(R.id.lastname);
        mAddress = (TextView)findViewById(R.id.address);
        mBack = (Button)findViewById(R.id.back);


        mRecyclerView = (RecyclerView)findViewById(R.id.purchase);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setHasFixedSize(true);

        mProductLayout = new LinearLayoutManager(CustomerSingleActivity.this);
        mRecyclerView.setLayoutManager(mProductLayout);
        mAdapter = new PurchaseAdapter(getDataSetHistory(), CustomerSingleActivity.this);
        mRecyclerView.setAdapter(mAdapter);


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

        getProductIds();
    }

    private void getProductIds() {

        DatabaseReference mProductIds = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(customerId).child("Purchase");
        mProductIds.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot products : dataSnapshot.getChildren()){
                        getProductInfo(products.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getProductInfo(String key) {

        DatabaseReference bookDB = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(customerId).child("Purchase").child(key);
        bookDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot books1 : dataSnapshot.getChildren()){
                        if(books1.getKey().equals("bookID")){
                            bookId = books1.getValue().toString();
                            getBookInfo(bookId);
                        }
                        if(books1.getKey().equals("Date")){
                            date = books1.getValue().toString();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getBookInfo(String bookId) {

        DatabaseReference booksDB = FirebaseDatabase.getInstance().getReference().child("Books").child(bookId);
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

                    PurchaseObject purchased = new PurchaseObject(bookId, title, author, price, profileImageUrl, date);
                    resultProduct.add(purchased);
                    productList.add(purchased);
                    mAdapter.notifyDataSetChanged();
                    System.out.println(resultProduct);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    private ArrayList<PurchaseObject> productList = new ArrayList<>();


    private ArrayList resultProduct = new ArrayList<PurchaseObject>();

    private ArrayList<PurchaseObject> getDataSetHistory() {
        return resultProduct;
    }
}
