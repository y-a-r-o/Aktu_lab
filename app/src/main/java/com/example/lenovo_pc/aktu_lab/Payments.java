package com.example.lenovo_pc.aktu_lab;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.Objects;

public class Payments extends AppCompatActivity implements PaymentResultListener {

    private Button buttonConfirmOrder;
//    String payment = getString(R.string.pay);
//    String string;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    LabDetailsClass labDetailsClass;
    MyBookingClass myBookingClass;
    String category;
    int key;

    int payment_final;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Log.w("payment_error","beforesharedpreference");             //LOG
        SharedPreferences spf =getSharedPreferences("TimeSlot",MODE_PRIVATE);
        payment_final= spf.getInt("Price",0);
        Log.w("payment_error","aftersharedpreference");             //LOG

        spf = getSharedPreferences("LabDetails",MODE_PRIVATE);
        category = spf.getString("category",null);
        key = spf.getInt("key",0);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

//        Query query = databaseReference.child("labs").child(categotry).child(""+key);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                labDetailsClass = dataSnapshot.getValue(LabDetailsClass.class);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



        findViews();
        listeners();
    }

    public void findViews() {
        buttonConfirmOrder = findViewById(R.id.buttonConfirmOrder);
    }


    public void listeners() {


        buttonConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
    }


    public void startPayment() {

        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Toll Charges");
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");


            Log.w("payment_error",""+payment_final);                            //LOG

//            String payment = getString(R.string.pay);
            String payment = Integer.toString(payment_final);

            Log.w("payment_error",""+payment);                             //LOG
            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "");
            preFill.put("contact", "");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Log.w("payment_error","catch");
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Toast.makeText(this, "Payment successfully done! " + razorpayPaymentID, Toast.LENGTH_SHORT).show();

        String temp = firebaseAuth.getCurrentUser().getUid();
        myBookingClass = new MyBookingClass("labs",category,key);
        String push_key = databaseReference.push().getKey();
        databaseReference.child("users").child(temp).child("mybookings").child(""+push_key).setValue(myBookingClass);

        Intent intent = new Intent(Payments.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e);
        }
    }
}