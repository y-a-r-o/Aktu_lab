package com.example.lenovo_pc.aktu_lab;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MyBookingDetailsActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    Context mContext;
    BookingLabDetailsClass details;
    BookingUserDetailsClass bookingUserDetailsClass;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    //for dots
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    //upto here
    TextView name;
    TextView collegename;
    TextView description;
    TextView address;

    ScrollView scrollView;
    int previousScrollY;

    long node;
    CollegeContactsClass collegeContactsClass;
    TimeslotClass timeslotClass;
    TextView Email;
    TextView Website;
    TextView Phone;

    String uid;
    TextView date;
    TextView time;
    TextView price;


    TextView studentname;
    TextView college;
    TextView studentphone;
    TextView studentrollno;
    TextView transid;


    CardView cardView;
    CardView cardView2;


    private static final String TAG = "LabDetailsActivity";


    //    final int key = getIntent().getIntExtra("key", 0);
//    final String category = getIntent().getStringExtra("category");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_my_booking_details);
        final String key = getIntent().getStringExtra("key");
        final String category = getIntent().getStringExtra("category");
//        Toast.makeText(getApplicationContext(), "category=" + category + " key=" + key, Toast.LENGTH_SHORT).show();
        Log.w("MyBookingsDetailsActiv",category);

        mContext = getApplicationContext();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference.keepSynced(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        name = (TextView) findViewById(R.id.name2);
        collegename = (TextView) findViewById(R.id.collegename2);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);    //for dots
        description = (TextView) findViewById(R.id.description1);
        address = (TextView) findViewById(R.id.address1);

        cardView = findViewById(R.id.cardView4);
        cardView2 = findViewById(R.id.pay_unsuccess);
        cardView2.setVisibility(View.GONE);

        Email = (TextView) findViewById(R.id.email1);
        Website = (TextView) findViewById(R.id.website2);
        Phone = (TextView) findViewById(R.id.phone1);

        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        price = findViewById(R.id.price);

        //from here

        studentname = findViewById(R.id.studentname);
        college = findViewById(R.id.college);
        studentphone = findViewById(R.id.studentphone);
        studentrollno = findViewById(R.id.rollno);
        transid = findViewById(R.id.transid);



        scrollView = (ScrollView) findViewById(R.id.scrollview);
        previousScrollY = scrollView.getScrollY();

        node = 1540231525137L;

        uid = firebaseAuth.getCurrentUser().getUid();
        Log.w("MyBookingsDetailsActiv",uid);
        Query query = databaseReference.child("users").child(uid).child("mybookings").child(key);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    details = dataSnapshot.getValue(BookingLabDetailsClass.class);
                    name.setText(details.getName());
                    Log.w("MyBookingsDetailsActiv", details.getName());
                    collegename.setText(details.getCollege_name());
                    Log.w("MyBookingsDetailsActiv", details.getCollege_name());
                    description.setText(details.getDescription());
                    Log.w("MyBookingsDetailsActiv", details.getDescription());

                    address.setText(details.getAddress());
                    String[] mImages = {details.getImage1(), details.getImage2(), details.getImage3(), details.getCardimage()};
                    viewPagerAdapter = new ViewPagerAdapter(mContext, mImages);
                    viewPager.setAdapter(viewPagerAdapter);
                    //for do
                    dotscount = viewPagerAdapter.getCount();
//                    dotscount = 4;
                    dots = new ImageView[dotscount];
                    for (int i = 0; i < dotscount; i++) {
                        dots[i] = new ImageView(mContext);
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(8, 0, 8, 0);
                        sliderDotspanel.addView(dots[i], params);
                    }
                    dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        }

                        @Override
                        public void onPageSelected(int position) {
                            for (int i = 0; i < dotscount; i++) {
                                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                            }
                            dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {
                        }
                    });
                }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, "Database Error Occured...", Toast.LENGTH_SHORT).show();
            }
        });


        ValueEventListener postListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                collegeContactsClass = dataSnapshot.child("users").child(uid).child("mybookings").child(key).child("college_contacts").getValue(CollegeContactsClass.class);
                Email.setText(collegeContactsClass.getEmail());
                Website.setText(collegeContactsClass.getWebsite());
                Phone.setText(collegeContactsClass.getPhone_no());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, "Database Error Occured...", Toast.LENGTH_SHORT).show();
            }
        };
        databaseReference.addValueEventListener(postListener2);


        ValueEventListener postListener3 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookingUserDetailsClass= dataSnapshot.child("users").child(uid).child("mybookings").child(key).child("personal_details").getValue(BookingUserDetailsClass.class);
                String temp = "Name - "+bookingUserDetailsClass.getName();
                studentname.setText(temp);
                temp = "College - "+bookingUserDetailsClass.getCollege();
                college.setText(temp);
                temp = "Phone Number- "+bookingUserDetailsClass.getPhone();
                studentphone.setText(temp);
                temp = "Roll Number - "+bookingUserDetailsClass.getRollno();
                studentrollno.setText(temp);
                temp = "Transaction ID - "+bookingUserDetailsClass.getTransaction_id();
                transid.setText(temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, "Database Error Occured...", Toast.LENGTH_SHORT).show();
            }
        };
        databaseReference.addValueEventListener(postListener3);


        databaseReference = firebaseDatabase.getReference().child("users").child(uid).child("mybookings").child(key);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("time_slot")) {
                    ValueEventListener postListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            timeslotClass = dataSnapshot.child("users").child(uid).child("mybookings").child(key).child("time_slot").getValue(TimeslotClass.class);
                                String temp = "Date - " + timeslotClass.getDate();
                                date.setText(temp);
                                temp = "Time - " + timeslotClass.getTime();
                                time.setText(temp);
                                temp = "Price - " + timeslotClass.getPrice();
                                price.setText(temp);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(mContext, "Database Error Occured...", Toast.LENGTH_SHORT).show();
                        }
                    };
                    databaseReference.addValueEventListener(postListener);
                }
                else{
                    cardView.setVisibility(View.GONE);
                    cardView2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



//    });


    }
}
