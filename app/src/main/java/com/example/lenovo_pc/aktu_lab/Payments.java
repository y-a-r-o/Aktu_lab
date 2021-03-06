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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
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
    BookingLabDetailsClass bookingLabDetailsClass;
    CollegeContactsClass collegeContactsClass;
    TimeslotClass timeslotClass;
    MyBookingClass myBookingClass;
    String category;
    int key;
    String main;
    BookingUserDetailsClass bookingUserDetailsClass;

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

        spf = getSharedPreferences("LabDetails",MODE_PRIVATE);      //to get path detail
        main = spf.getString("main",null);
        category = spf.getString("category",null);
        key = spf.getInt("key",0);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();




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

                Log.w("payment_error", "" + payment_final);                            //LOG

//            String payment = getString(R.string.pay);
                String payment = Integer.toString(payment_final);

                Log.w("payment_error", "" + payment);                             //LOG
                double total = Double.parseDouble(payment);
                total = total * 100;
                options.put("amount", total);

                JSONObject preFill = new JSONObject();
                preFill.put("email", "");
                preFill.put("contact", "");

                options.put("prefill", preFill);

                co.open(activity, options);
            } catch (Exception e) {
                Log.w("payment_error", "catch");
                Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
    }


    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {

        final String temp_razorpayPaymentID = razorpayPaymentID;
        Toast.makeText(this, "Payment successfully done! " + razorpayPaymentID, Toast.LENGTH_SHORT).show();

        SharedPreferences spf = getSharedPreferences("TimeSlot",MODE_PRIVATE);
        timeslotClass = new TimeslotClass(spf.getString("Date",null),spf.getInt("Price",0),spf.getString("Time",null));
        final String temp_temp = spf.getString("Date",null);
        databaseReference= firebaseDatabase.getReference();
        databaseReference.child(main).child(category).child(""+key).child("time_slot").orderByChild("date").equalTo(temp_temp).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String clubkey = childSnapshot.getKey();
                    Log.w("clubkey",clubkey);

                    DatabaseReference databaseReference1 = firebaseDatabase.getReference();
                    databaseReference1 = databaseReference.child(main).child(category).child(""+key).child("time_slot").child(clubkey).child("seats");
                    databaseReference1.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            Integer currentValue = mutableData.getValue(Integer.class);
                            Log.w("payment i= ",""+currentValue);

                            Integer temp_integer = new Integer(""+currentValue);
                            int i= Integer.parseInt(temp_integer.toString());

                            Log.w("payment i= ",""+i);

                            if (currentValue == null) {
                                Log.w("Transaction","null");

                                Intent intent = new Intent(getApplicationContext(),RefundActivity.class);
                                startActivity(intent);

                                return Transaction.abort();
                            }
                            else if(i==0){
                                Log.w("Transaction","value is zero");
                                SharedPreferences spf =getSharedPreferences("CollegeContacts",MODE_PRIVATE);
                                collegeContactsClass = new CollegeContactsClass(spf.getString("email",null),spf.getString("website",null),spf.getString("phoneno",null));

                                spf = getSharedPreferences("BOOKINGUSER",MODE_PRIVATE);
                                String json = spf.getString("bookinguser","");
                                Gson gson = new Gson();
                                bookingUserDetailsClass = gson.fromJson(json,BookingUserDetailsClass.class);


                                final String temp = firebaseAuth.getCurrentUser().getUid();
                                myBookingClass = new MyBookingClass("labs",category,key);

                                databaseReference = firebaseDatabase.getReference();
                                Query query = databaseReference.child(main).child(category).child(""+key);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        labDetailsClass = dataSnapshot.getValue(LabDetailsClass.class);
                                        String push_key = databaseReference.push().getKey();
                                        bookingLabDetailsClass = new BookingLabDetailsClass(labDetailsClass.getAddress(),labDetailsClass.getCardimage(),labDetailsClass.getCollege_name(),labDetailsClass.getDescription(),labDetailsClass.getImage1(),labDetailsClass.getImage2(),labDetailsClass.getImage3(),push_key,labDetailsClass.getName(),labDetailsClass.getPrice_tag(),labDetailsClass.getCollege_key());
                                        databaseReference.child("users").child(temp).child("mybookings").child(""+push_key).setValue(bookingLabDetailsClass);
//                                        databaseReference.child("users").child(temp).child("mybookings").child(""+push_key).child("time_slot").setValue(timeslotClass);
                                        databaseReference.child("users").child(temp).child("mybookings").child(""+push_key).child("college_contacts").setValue(collegeContactsClass);
                                        databaseReference.child("users").child(temp).child("mybookings").child(""+push_key).child("personal_details").setValue(bookingUserDetailsClass);
                                        databaseReference.child("users").child(temp).child("mybookings").child(""+push_key).child("personal_details").child("transaction_id").setValue(temp_razorpayPaymentID);
                                        //to save data in the booking faliure class
                                        databaseReference = firebaseDatabase.getReference();
                                        databaseReference.child("bookings_faliure").child(""+push_key).child("personal_details").setValue(bookingUserDetailsClass);
                                        databaseReference.child("bookings_faliure").child(""+push_key).child("personal_details").child("transaction_id").setValue(temp_razorpayPaymentID);
                                        databaseReference.child("bookings_faliure").child(""+push_key).child("personal_details").child("refunded").setValue("false");
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });



                                Intent intent = new Intent(getApplicationContext(),BookingFaliure.class);
                                startActivity(intent);

                                return Transaction.abort();
                            }
                            else {
                                mutableData.setValue(currentValue - 1);
                                SharedPreferences spf =getSharedPreferences("CollegeContacts",MODE_PRIVATE);
                                collegeContactsClass = new CollegeContactsClass(spf.getString("email",null),spf.getString("website",null),spf.getString("phoneno",null));

                                spf = getSharedPreferences("BOOKINGUSER",MODE_PRIVATE);
                                String json = spf.getString("bookinguser","");
                                Gson gson = new Gson();
                                bookingUserDetailsClass = gson.fromJson(json,BookingUserDetailsClass.class);


                                final String temp = firebaseAuth.getCurrentUser().getUid();
                                myBookingClass = new MyBookingClass("labs",category,key);

                                databaseReference = firebaseDatabase.getReference();
                                Query query = databaseReference.child(main).child(category).child(""+key);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        labDetailsClass = dataSnapshot.getValue(LabDetailsClass.class);
                                        String push_key = databaseReference.push().getKey();
                                        bookingLabDetailsClass = new BookingLabDetailsClass(labDetailsClass.getAddress(),labDetailsClass.getCardimage(),labDetailsClass.getCollege_name(),labDetailsClass.getDescription(),labDetailsClass.getImage1(),labDetailsClass.getImage2(),labDetailsClass.getImage3(),push_key,labDetailsClass.getName(),labDetailsClass.getPrice_tag(),labDetailsClass.getCollege_key());
                                        databaseReference.child("users").child(temp).child("mybookings").child(""+push_key).setValue(bookingLabDetailsClass);
                                        databaseReference.child("users").child(temp).child("mybookings").child(""+push_key).child("time_slot").setValue(timeslotClass);
                                        databaseReference.child("users").child(temp).child("mybookings").child(""+push_key).child("college_contacts").setValue(collegeContactsClass);
                                        databaseReference.child("users").child(temp).child("mybookings").child(""+push_key).child("personal_details").setValue(bookingUserDetailsClass);
                                        databaseReference.child("users").child(temp).child("mybookings").child(""+push_key).child("personal_details").child("transaction_id").setValue(temp_razorpayPaymentID);
                                        //to save data in the booking faliure class
                                        databaseReference = firebaseDatabase.getReference();
                                        databaseReference.child("bookings_done").child(labDetailsClass.getCollege_key()).child(""+labDetailsClass.getKey()).child(timeslotClass.getDate()).child(""+push_key).child("personal_details").setValue(bookingUserDetailsClass);
                                        databaseReference.child("bookings_done").child(labDetailsClass.getCollege_key()).child(""+labDetailsClass.getKey()).child(timeslotClass.getDate()).child(""+push_key).child("personal_details").child("transaction_id").setValue(temp_razorpayPaymentID);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                Intent intent = new Intent(Payments.this,MainActivity.class);
                                startActivity(intent);
                            }
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                            Log.w("Transaction","Value should be decremented");
                        }
                    });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


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