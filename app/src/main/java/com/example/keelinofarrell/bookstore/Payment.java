package com.example.keelinofarrell.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Payment extends AppCompatActivity {

    CardForm mCardForm;
    TextView mPay;
    Button mConfirm, mBack;
    FirebaseAuth mAuth;
    String userId, mCardNum, mCVV, mExpiryMonth, mExpYear, mCardname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mCardForm = (CardForm)findViewById(R.id.cardform);
        mPay = (TextView)findViewById(R.id.payment_amount);
        mConfirm = (Button)findViewById(R.id.btn_pay);
        mBack = (Button)findViewById(R.id.back);

        mPay.setText("");
        mConfirm.setText("Confirm");

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Payment.this, CustomerProfile.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mCardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                Toast.makeText(Payment.this, "Name : " + card.getName(), Toast.LENGTH_SHORT).show();

                DatabaseReference mCardinfo = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(userId);

                mCardNum = card.getNumber().toString();
                mCardname = card.getName().toString();
                mCVV = card.getCVC().toString();
                mExpiryMonth = card.getExpMonth().toString();
                mExpYear = card.getExpYear().toString();

                Map payinfo = new HashMap();
                payinfo.put("CardName" , mCardname);
                payinfo.put("CardNumber" , mCardNum);
                payinfo.put("CardCVC" , mCVV);
                payinfo.put("CardExpMonth" , mExpiryMonth);
                payinfo.put("CardExpYear" , mExpYear);

                mCardinfo.child("CardInfo").push().setValue(payinfo);

                Intent intent = new Intent(Payment.this, CustomerProfile.class);
                startActivity(intent);
                finish();
                return;

            }
        });

    }
}
