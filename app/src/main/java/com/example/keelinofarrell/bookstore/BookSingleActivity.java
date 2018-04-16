package com.example.keelinofarrell.bookstore;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.keelinofarrell.bookstore.BookRecyclerInfo.BookAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BookSingleActivity extends AppCompatActivity {

    private EditText mTitle, mISBN, mAuthor, mPrice, mStock, mCategory;
    private TextView mText;
    private ImageView mBookImage, mMainImage;
    private Button mConfirm, mBack, mDelete;
    String bookId;
    String mTitle1, mAuthor1, mISBN1, mPrice1, mStock1, mCategory1, mProfileUrl, userId, mQuantity2;
    DatabaseReference booksinfo, user;
    private Uri resultUri;
    private BookAdapter mBookAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_single);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mDelete = (Button)findViewById(R.id.delete);

        mText = (TextView)findViewById(R.id.bookdetails);
        mTitle = (EditText)findViewById(R.id.BookTitle);
        mAuthor = (EditText)findViewById(R.id.BookAuthor);
        mPrice = (EditText)findViewById(R.id.BookPrice);
        mISBN = (EditText)findViewById(R.id.BookISBN);
        mStock = (EditText)findViewById(R.id.BookQuantity);
        mMainImage = (ImageView)findViewById(R.id.backdrop);
        mBookImage = (ImageView)findViewById(R.id.bookCover);
        mPrice = (EditText)findViewById(R.id.BookPrice);
        mCategory = (EditText)findViewById(R.id.BookCategory);
        mConfirm = (Button)findViewById(R.id.change);
        mBack = (Button)findViewById(R.id.back);



        user = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(userId);
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mTitle.setEnabled(false);
                    mAuthor.setEnabled(false);
                    mPrice.setEnabled(false);
                    mISBN.setEnabled(false);
                    mStock.setEnabled(false);
                    mCategory.setEnabled(false);

                    mBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(BookSingleActivity.this, CustomerBookActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    });
                    mDelete.setText("Leave/Read Reviews");
                    mDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(BookSingleActivity.this, BookReview.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("bookId", bookId);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            bookId = extras.getString("bookId");
        }

        booksinfo = FirebaseDatabase.getInstance().getReference().child("Books").child(bookId);

        getBookInfo();



        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookSingleActivity.this, AdminBookActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBookInfo();
                Intent intent = new Intent(BookSingleActivity.this, AdminBookActivity.class);
                startActivity(intent);
                finish();
                return;

            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBook();
            }
        });


    }

    private void findTheQuantity() {

        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if(("0").equals(mStock.getText().toString())){
                        mConfirm.setText("Out Of Stock");
                        mConfirm.setClickable(false);
                    }else {
                        mConfirm.setText("Add to Cart");
                        mConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(), "Added to Cart", Toast.LENGTH_LONG).show();
                                addBooktoCart();

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void addBooktoCart() {

        DatabaseReference customercartRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(userId).child("cart");
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("cart");

        HashMap cartMap = new HashMap();
        cartMap.put("bookID", bookId);

        customercartRef.push().setValue(cartMap);

    }

    private void deleteBook() {
        booksinfo.removeValue();
        Intent intent = new Intent(BookSingleActivity.this, AdminBookActivity.class);
        startActivity(intent);
        finish();
        return;

    }

    //method to update book if admin changes any of the book details
    private void saveBookInfo() {
        mTitle1 = mTitle.getText().toString();
        mAuthor1 = mAuthor.getText().toString();
        mISBN1 = mISBN.getText().toString();
        mCategory1 = mCategory.getText().toString();
        mPrice1 = mPrice.getText().toString();
        mStock1 = mStock.getText().toString();

        Map bookinfo = new HashMap();
        bookinfo.put("Title", mTitle1);
        bookinfo.put("Author", mAuthor1);
        bookinfo.put("ISBN", mISBN1);
        bookinfo.put("Category", mCategory1);
        bookinfo.put("Price", mPrice1);
        bookinfo.put("Stock", mStock1);

        booksinfo.updateChildren(bookinfo);


        if(resultUri != null){
            StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_images").child(bookId);
            Bitmap bitmap = null;

            //Pass resultURI into a bitmap
            //gets image from uri location
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //compress image
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            //move image into an array, how you save images in Firebase
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filePath.putBytes(data);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                    return;
                }
            });

            //add onclicklisteners to see if upload was successful
            //add URL to the Firebase database
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //get download URL
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Map newImage = new HashMap();
                    newImage.put("profileImageUrl", downloadUrl.toString());
                    booksinfo.updateChildren(newImage);

                    finish();
                    return;
                }
            });

            mBookAdapter.notifyDataSetChanged();
        }else {

            finish();
        }


    }

    //method to populate edittexts with book info
    private void getBookInfo() {
        booksinfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){
                    for(DataSnapshot child : dataSnapshot.getChildren()){
                        if(child.getKey().equals("ISBN")){
                            mISBN.setText(child.getValue().toString());
                        }
                        if(child.getKey().equals("Title")){
                            mTitle.setText(child.getValue().toString());
                        }
                        if(child.getKey().equals("Author")){
                            mAuthor.setText(child.getValue().toString());
                        }
                        if(child.getKey().equals("Category")){
                            mCategory.setText(child.getValue().toString());
                        }
                        if(child.getKey().equals("Price")){
                            mPrice.setText(child.getValue().toString());
                        }
                        if(child.getKey().equals("Stock")){
                            mStock.setText(child.getValue().toString());
                            findTheQuantity();
                        }
                        if(child.getKey().equals("profileImageURL")){
                            mProfileUrl = child.getValue().toString();
                            Glide.with(getApplication()).load(mProfileUrl).into(mBookImage);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
