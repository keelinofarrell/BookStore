package com.example.keelinofarrell.bookstore;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keelinofarrell.bookstore.ShoppingRecyclerInfo.ProductAdapter;
import com.example.keelinofarrell.bookstore.ShoppingRecyclerInfo.ProductObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class CongratsActivity extends AppCompatActivity {

    Button mBack;
    TextView mTotal, mUsername;
    String userId, bookId;
    private RecyclerView mRecyclerView;
    private ProductAdapter mAdapter;
    private RecyclerView.LayoutManager mProductLayout;
    private FirebaseAuth mAuth;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congrats);

        mTotal = (TextView)findViewById(R.id.Total);
        mBack = (Button)findViewById(R.id.back);
        mUsername = (TextView)findViewById(R.id.username);
        mImage = (ImageView)findViewById(R.id.thankyou);


        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        mRecyclerView = (RecyclerView)findViewById(R.id.cart);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setHasFixedSize(true);

        mProductLayout = new LinearLayoutManager(CongratsActivity.this);
        mRecyclerView.setLayoutManager(mProductLayout);
        mAdapter = new ProductAdapter(getDataSetHistory(), CongratsActivity.this);
        mRecyclerView.setAdapter(mAdapter);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CongratsActivity.this, CustomerBookActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        getUsername();

        getProductIds();
    }

    private void getUsername() {

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mUsername.setText("Thank you for your purchase " + dataSnapshot.child("firstname").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getProductIds() {

        DatabaseReference mProductIds = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(userId).child("cart");
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

        DatabaseReference bookDB = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(userId).child("cart").child(key);
        bookDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot books1 : dataSnapshot.getChildren()){
                        if(books1.getKey().equals("bookID")){
                            bookId = books1.getValue().toString();
                            getBookInfo(bookId);


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

                    ProductObject prod = new ProductObject(bookId, title, author, price, profileImageUrl);
                    resultProduct.add(prod);
                    productList.add(prod);
                    mAdapter.notifyDataSetChanged();
                    System.out.println(resultProduct);
                }

                getTotal();
                clearCart();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void clearCart(){

        final DatabaseReference clearCartRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(userId).child("cart");
        clearCartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    clearCartRef.removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getTotal() {
        mTotal.setText("Total Price: " + "€" + mAdapter.grandTotal(resultProduct));
    }


    private ArrayList<ProductObject> productList = new ArrayList<>();


    private ArrayList resultProduct = new ArrayList<ProductObject>();

    private ArrayList<ProductObject> getDataSetHistory() {
        return resultProduct;
    }
}
