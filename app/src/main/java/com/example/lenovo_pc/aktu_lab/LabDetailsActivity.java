package com.example.lenovo_pc.aktu_lab;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LabDetailsActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Context mContext;
    LabDetailsClass details;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    //for dots
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    //upto here
    TextView name;
    TextView collagename;
    TextView description;
    TextView address;

    ScrollView scrollView;
    FloatingActionButton floatingActionButton;
    int previousScrollY;

    TimeClass timeClass;
    long node ;
    CollegeContactsClass collegeContactsClass;
    TimeSlotClass timeSlotClass;
    TextView Email;
    TextView Website;
    TextView Phone;

    RadioGroup radioGroup;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    RadioButton radioButton5;
    RadioButton radioButton6;
    RadioButton radioButton7;

    int dot=0;   // to control the duplication of the slider dots

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_details);
        final int key = getIntent().getIntExtra("key", 0);
        final String category = getIntent().getStringExtra("category");
//        Toast.makeText(getApplicationContext(), "category=" + category + " key=" + key, Toast.LENGTH_SHORT).show();

        mContext = getApplicationContext();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        name = (TextView) findViewById(R.id.name1);
        collagename = (TextView) findViewById(R.id.collagename1);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);    //for dots
        description = (TextView) findViewById(R.id.description);
        address = (TextView) findViewById(R.id.address);

        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        radioButton1  =(RadioButton) findViewById(R.id.radioButton1);
        radioButton2  =(RadioButton) findViewById(R.id.radioButton2);
        radioButton3  =(RadioButton) findViewById(R.id.radioButton3);
        radioButton4  =(RadioButton) findViewById(R.id.radioButton4);
        radioButton5  =(RadioButton) findViewById(R.id.radioButton5);
        radioButton6  =(RadioButton) findViewById(R.id.radioButton6);
        radioButton7  =(RadioButton) findViewById(R.id.radioButton7);

        Email = (TextView) findViewById(R.id.email);
        Website = (TextView) findViewById(R.id.website);
        Phone = (TextView) findViewById(R.id.phone);

        scrollView = (ScrollView)findViewById(R.id.scrollview);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.bookbtn);
        previousScrollY = scrollView.getScrollY();

        node= 1540231525137L;


        Query query = databaseReference.child("labs").child(category).child(""+key);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                details = dataSnapshot.getValue(LabDetailsClass.class);
                name.setText(details.getName());
                collagename.setText(details.getCollage_name());
                description.setText(details.getDescription());
                address.setText(details.getAddress());
                String[] mImages = {details.getImage1(), details.getImage2(), details.getImage3(), details.getCardimage()};
                viewPagerAdapter = new ViewPagerAdapter(mContext, mImages);
                viewPager.setAdapter(viewPagerAdapter);
                //for do
                dotscount = viewPagerAdapter.getCount();
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



        //upto here for dots
        ValueEventListener postListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                collegeContactsClass = dataSnapshot.child("labs").child(category).child("" + key).child("collage_contacts").getValue(CollegeContactsClass.class);

//                String tempstring = "Email - " + collegeContactsClass.getEmail();
                Email.setText(collegeContactsClass.getEmail());
//                tempstring = "Website - " + collegeContactsClass.getWebsite();
                Website.setText(collegeContactsClass.getWebsite());
//                tempstring = "Phone - " + collegeContactsClass.getPhone_no();
                Phone.setText(collegeContactsClass.getPhone_no());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, "Database Error Occured...", Toast.LENGTH_SHORT).show();
            }
        };
        databaseReference.addValueEventListener(postListener2);


        //date and time

            databaseReference = firebaseDatabase.getReference();
            String key_time = databaseReference.push().getKey();
            Map<String, Object> value = new HashMap<>();
            value.put("purpose", "To get time.");
            value.put("timestamp", ServerValue.TIMESTAMP);
            databaseReference.child(key_time).setValue(value);

            Query query_date = databaseReference.child(key_time);
            query_date.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    timeClass = dataSnapshot.getValue(TimeClass.class);
                    node = timeClass.getTimestamp();
//                    Toast.makeText(LabDetailsActivity.this, "" + timeClass.getTimestamp(), Toast.LENGTH_SHORT).show();
                    Date date = new Date(node);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
                    String current_date=sdf.format(date);

//                    Toast toast = Toast.makeText(LabDetailsActivity.this, "Date and Time= " + current_date, Toast.LENGTH_SHORT);
//                    toast.show();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            databaseReference = databaseReference.child(key_time);
            databaseReference.setValue(null);



//        for(int k=0;k<7;k++){
//
//            databaseReference=firebaseDatabase.getReference();
//            Query query_time_slots=databaseReference.child("labs").child(category).child(""+key).child("time_slot").equalTo();
//
//        }

        // date and time

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if(scrollView.getScrollY()>previousScrollY && floatingActionButton.getVisibility() == View.VISIBLE){
                    floatingActionButton.hide();
                }
                else if(scrollView.getScrollY()<previousScrollY && floatingActionButton.getVisibility() != View.VISIBLE){
                    floatingActionButton.show();
                }
                previousScrollY = scrollView.getScrollY();
            }
        });


//    });

       //all this is for testing to get the date from firebase
       floatingActionButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(radioGroup.getCheckedRadioButtonId()==-1){

                   Toast.makeText(getApplicationContext(),"Please select the radiobutton.",Toast.LENGTH_LONG).show();
               }
               else {
//                   databaseReference = firebaseDatabase.getReference();
//                   String key = databaseReference.push().getKey();
//                   Map<String, Object> value = new HashMap<>();
//                   value.put("purpose", "To get time.");
//                   value.put("timestamp", ServerValue.TIMESTAMP);
//                   databaseReference.child(key).setValue(value);
//
//                   Query query = databaseReference.child(key);
//                   query.addListenerForSingleValueEvent(new ValueEventListener() {
//                       @Override
//                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                           timeClass = dataSnapshot.getValue(TimeClass.class);
//                           node = timeClass.getTimestamp();
//                           Toast.makeText(LabDetailsActivity.this, "" + timeClass.getTimestamp(), Toast.LENGTH_SHORT).show();
//                       }
//
//                       @Override
//                       public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                       }
//                   });
//
//                   databaseReference = databaseReference.child(key);
//                   databaseReference.setValue(null);
//
//                   Date date = new Date(node);
//                   SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
//                   sdf.format(date);
//                   Toast toast = Toast.makeText(LabDetailsActivity.this, "Date and Time= " + date, Toast.LENGTH_SHORT);
//                   toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                   toast.show();
               }



//               Toast.makeText(getApplicationContext(), "ServerValue.TIMESTAMP="+ServerValue.TIMESTAMP, Toast.LENGTH_SHORT).show();
//               Toast toast  =Toast.makeText(LabDetailsActivity.this, "value.get('timestamp')"+value.get("timestamp"), Toast.LENGTH_SHORT);
//               toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
//               toast.show();
           }
       });

       //up to here is for getting time from firebase

    }
}