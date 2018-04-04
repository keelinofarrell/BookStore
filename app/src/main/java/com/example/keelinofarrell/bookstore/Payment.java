package com.example.keelinofarrell.bookstore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;

import org.w3c.dom.Text;

public class Payment extends AppCompatActivity {

    CardForm mCardForm;
    TextView mPay;
    Button mConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mCardForm = (CardForm)findViewById(R.id.cardform);
        mPay = (TextView)findViewById(R.id.payment_amount);
        mConfirm = (Button)findViewById(R.id.btn_pay);

        mPay.setText("");
        mConfirm.setText("Confirm");

        mCardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                Toast.makeText(Payment.this, "Name : " + card.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
