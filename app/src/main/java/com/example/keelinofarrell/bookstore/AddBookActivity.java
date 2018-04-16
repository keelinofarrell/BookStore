package com.example.keelinofarrell.bookstore;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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


public class AddBookActivity extends AppCompatActivity {

    private EditText mISBN, mTitle, mAuthor, mCategory, mPrice, mQuantity;
    private Button mConfirm, mBack;
    private DatabaseReference mBookDatabase;
    private String mISBN1, mTitle1, mAuthor1, mCategory1, mPrice1, mQuantity1, bookId;

    private ImageView mBookImage;
    private Uri resultUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        mISBN = (EditText)findViewById(R.id.isbn);
        mTitle = (EditText)findViewById(R.id.title);
        mAuthor = (EditText)findViewById(R.id.author);
        mCategory = (EditText)findViewById(R.id.category);
        mPrice = (EditText)findViewById(R.id.price);
        mBookImage = (ImageView) findViewById(R.id.bookImage);
        mConfirm = (Button)findViewById(R.id.confirm);
        mBack = (Button)findViewById(R.id.back);
        mQuantity = (EditText)findViewById(R.id.quantity);


        mBookDatabase = FirebaseDatabase.getInstance().getReference().child("Books");
        mBookDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot child : dataSnapshot.getChildren()){
                        bookId = child.getKey().toString();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
                    String userInput = "" + editable.toString().replaceAll("[^\\d]", "");
                    StringBuilder cashAmountBuilder = new StringBuilder(userInput);

                    while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                        cashAmountBuilder.deleteCharAt(0);
                    }
                    while (cashAmountBuilder.length() < 3) {
                        cashAmountBuilder.insert(0, '0');
                    }
                    cashAmountBuilder.insert(cashAmountBuilder.length() - 2, '.');

                    mPrice.removeTextChangedListener(this);
                    mPrice.setText(cashAmountBuilder.toString());

                    mPrice.setTextKeepState("€" + cashAmountBuilder.toString());
                    Selection.setSelection(mPrice.getText(), cashAmountBuilder.toString().length() + 1);

                    mPrice.addTextChangedListener(this);
                }

            }
        });

        mBookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBookInfo();
                Intent intent = new Intent(AddBookActivity.this, AdminBookActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBookActivity.this, AdminBookActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

    }



    //save the book info to the firebase database
    private void saveBookInfo() {
      mBookDatabase = FirebaseDatabase.getInstance().getReference().child("Books");



        if(resultUri != null){
            StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_images");
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

                    mISBN1 = mISBN.getText().toString();
                    mTitle1 = mTitle.getText().toString();
                    mAuthor1 = mAuthor.getText().toString();
                    mCategory1 = mCategory.getText().toString();
                    mPrice1 = mPrice.getText().toString();
                    mQuantity1 = mQuantity.getText().toString();
                    final Map bookInfo = new HashMap();
                    bookInfo.put("ISBN", mISBN1);
                    bookInfo.put("Title", mTitle1);
                    bookInfo.put("Author", mAuthor1);
                    bookInfo.put("Category", mCategory1);
                    bookInfo.put("Price", mPrice1);
                    bookInfo.put("Stock", mQuantity1);
                    bookInfo.put("profileImageURL", downloadUrl.toString());

                    mBookDatabase.push().setValue(bookInfo);

                }
            });




        }else {

            finish();
        }


    }



    //method for getting the profile image that is chosen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            //set image view to image we got from this activity result
            mBookImage.setImageURI(resultUri);
        }
    }
}
