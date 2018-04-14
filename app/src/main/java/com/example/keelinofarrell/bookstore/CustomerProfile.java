package com.example.keelinofarrell.bookstore;

import android.app.Activity;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
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

public class CustomerProfile extends AppCompatActivity {

    private EditText mFirstname, mLastname, mEmail, mAddress;
    private String mFirstname1, mLastname1, mEmail1, mAddress1;
    private Button mConfirm, mBack, mHistory;
    private RadioButton mPaymentYes, mPaymentNo;
    private RadioGroup mRadio;
    private String userId;
    private FirebaseAuth mAuth;
    private DatabaseReference mCustomerDatabase;
    private ImageView mProfileImage;
    private Uri resultUri;
    private String mProfileUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(userId);

        getUserInfo();

        mFirstname = (EditText)findViewById(R.id.firstname);
        mLastname = (EditText)findViewById(R.id.lastname);
        mEmail = (EditText)findViewById(R.id.email);
        mAddress = (EditText)findViewById(R.id.shipping);
        mConfirm = (Button)findViewById(R.id.confirm);
        mBack = (Button)findViewById(R.id.back);
        mHistory = (Button)findViewById(R.id.purchaseHistory);
        mPaymentYes = (RadioButton)findViewById(R.id.paymemtYes);
        mPaymentNo = (RadioButton)findViewById(R.id.paymentNo);
        mRadio = (RadioGroup)findViewById(R.id.radios);
        mProfileImage = (ImageView)findViewById(R.id.profileImage);

        mPaymentYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerProfile.this, Payment.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerProfile.this, CustomerBookActivity.class);
                startActivity(intent);
                finish();
                return;

            }
        });

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        DatabaseReference payment = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(userId).child("CardInfo");
        payment.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mPaymentYes.setText("Update card info");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void getUserInfo() {
        mCustomerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("firstname") != null){
                        mFirstname1 = map.get("firstname").toString();
                        mFirstname.setText(mFirstname1);
                    }
                    if(map.get("lastname") != null){
                        mLastname1 = map.get("lastname").toString();
                        mLastname.setText(mLastname1);
                    }
                    if(map.get("email") != null){
                        mEmail1 = map.get("email").toString();
                        mEmail.setText(mEmail1);
                    }
                    if(map.get("address") != null){
                        mAddress1 = map.get("address").toString();
                        mAddress.setText(mAddress1);
                    }
                    if(map.get("profileImageUrl") != null){
                        mProfileUrl = map.get("profileImageUrl").toString();
                        Glide.with(getApplication()).load(mProfileUrl).into(mProfileImage);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveUserInfo() {
        mFirstname1 = mFirstname.getText().toString();
        mLastname1 = mLastname.getText().toString();
        mEmail1 = mEmail.getText().toString();
        mAddress1 = mAddress.getText().toString();
        Map userinfo = new HashMap();
        userinfo.put("firstname", mFirstname1);
        userinfo.put("lastname" , mLastname1);
        userinfo.put("email", mEmail1);
        userinfo.put("address", mAddress1);
        mCustomerDatabase.updateChildren(userinfo);

        if(resultUri != null){
            StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_images").child(userId);
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
                    mCustomerDatabase.updateChildren(newImage);

                    finish();
                    return;
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
            mProfileImage.setImageURI(resultUri);
        }
    }
}
