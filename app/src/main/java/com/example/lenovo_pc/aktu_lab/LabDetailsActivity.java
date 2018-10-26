package com.example.lenovo_pc.aktu_lab;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    TextView collegename;
    TextView description;
    TextView address;

    ScrollView scrollView;
    FloatingActionButton floatingActionButton;
    int previousScrollY;

    TimeClass timeClass;
    long node;
    CollegeContactsClass collegeContactsClass;
    TimeslotClass timeslotClass;
    TextView Email;
    TextView Website;
    TextView Phone;

    RadioGroup radioGroup;
    RadioButton radioButton[] = new RadioButton[7];
    final String rbtime[] = {"time1", "time2", "time3", "time4", "time5", "time6", "time7",};
    final String rbprice[] = {"price1", "price2", "price3", "price4", "price5", "price6", "price7"};
    final String rbdate[] = {"date1", "date2", "date3", "date4", "date5", "date6", "date7",};
    private static final String TAG = "LabDetailsActivity";


    //    final int key = getIntent().getIntExtra("key", 0);
//    final String category = getIntent().getStringExtra("category");

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
        collegename = (TextView) findViewById(R.id.collegename1);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);    //for dots
        description = (TextView) findViewById(R.id.description);
        address = (TextView) findViewById(R.id.address);

        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        radioButton[0] = (RadioButton) findViewById(R.id.radioButton1);
        radioButton[1] = (RadioButton) findViewById(R.id.radioButton2);
        radioButton[2] = (RadioButton) findViewById(R.id.radioButton3);
        radioButton[3] = (RadioButton) findViewById(R.id.radioButton4);
        radioButton[4] = (RadioButton) findViewById(R.id.radioButton5);
        radioButton[5] = (RadioButton) findViewById(R.id.radioButton6);
        radioButton[6] = (RadioButton) findViewById(R.id.radioButton7);

        Email = (TextView) findViewById(R.id.email);
        Website = (TextView) findViewById(R.id.website);
        Phone = (TextView) findViewById(R.id.phone);

        scrollView = (ScrollView) findViewById(R.id.scrollview);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.bookbtn);
        previousScrollY = scrollView.getScrollY();

        node = 1540231525137L;


        Query query = databaseReference.child("labs").child(category).child("" + key);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                details = dataSnapshot.getValue(LabDetailsClass.class);
                name.setText(details.getName());
                collegename.setText(details.getCollege_name());
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
                collegeContactsClass = dataSnapshot.child("labs").child(category).child("" + key).child("college_contacts").getValue(CollegeContactsClass.class);
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
        Log.w("date_key_time", ""+key_time);
        Query query_date = databaseReference.child(key_time);
        query_date.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                timeClass = dataSnapshot.getValue(TimeClass.class);
                node = timeClass.getTimestamp();
                Log.w("date_node", ""+node);
                Date date = new Date(node);                                       //for testing
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");//for testing
                String today = sdf.format(date);//for testing
                Log.w("date_today", today);
                SharedPreferences.Editor editor = getSharedPreferences("DATE", MODE_PRIVATE).edit();
                editor.putString("current_date", today);
                editor.apply();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        databaseReference = databaseReference.child(key_time);
        databaseReference.setValue(null);

        SharedPreferences prefs = getSharedPreferences("DATE", MODE_PRIVATE);
        String restoredText = prefs.getString("current_date", null);
        Log.w("date_restoredText_Bif", ""+restoredText);

        if (restoredText != null) {
            String name = prefs.getString("current_date", "No name defined");//"No name defined" is the default value.
        }
        Toast.makeText(getApplicationContext(), restoredText, Toast.LENGTH_LONG).show();
        Log.w("date_restoredText_Aif", ""+restoredText);

        //to increment current date +2
        //Convert the string-date into Date-date
        try {
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(restoredText);
            Log.w("date_restoredText_inTry", ""+restoredText);
            Log.w("Radiobutton hidden", "Date date=" + date);
            //increment the date by one
            Date dayAfter = new Date(date.getTime() + TimeUnit.DAYS.toMillis(2));
            Log.w("Radiobutton hidden", "Date dayAfter" + dayAfter);
            //convert Date-date back to String-date
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");//for testing
            restoredText = sdf.format(dayAfter);//for testing
            SharedPreferences.Editor editor = getSharedPreferences("DATE", MODE_PRIVATE).edit();
            editor.putString("current_date", restoredText);
            editor.apply();
            Log.w("date_restoredText_eTry", ""+restoredText);
        }catch (java.text.ParseException e) {
            Log.w("Radiobutton hidden", "catch...");
            e.printStackTrace();
        }

        // upto here date and time

        //function to set the radio buttons;
        for (int k = 0; k < 7; k++) {
            //to get the sharedpreference current_date
            prefs = getSharedPreferences("DATE", MODE_PRIVATE);
            restoredText = prefs.getString("current_date", null);
            if (restoredText != null) {
                String name = prefs.getString("current_date", "No name defined");//"No name defined" is the default value.
            }
            Log.w("date_restoredText_Try2", "" + restoredText);
            //serach firebase for today's date
            databaseReference = firebaseDatabase.getReference();
            final int z = k;

            Query query_date_details = databaseReference.child("labs").child(category).child(""+key).child("time_slot").orderByChild("date").equalTo(restoredText);           // have not used orderby
            query_date_details.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                    for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren()) {
                        timeslotClass = dataSnapshot2.getValue(TimeslotClass.class);                             //null values are being imported
                        // Log.w("apple",timeslotClass.getDate());
                        Log.d("getDate", "" + timeslotClass.getDate());

                        SharedPreferences.Editor editor = getSharedPreferences("DATE", MODE_PRIVATE).edit();
//                          editor.putString(rbdate[z],timeslotClass.getDate());
                        editor.putString(rbdate[z],timeslotClass.getDate() );
                        editor.putString(rbtime[z], timeslotClass.getTime());
                        editor.putInt(rbprice[z], timeslotClass.getPrice());
                        editor.apply();
                        Log.w("apple", rbdate[z]);

                        SharedPreferences prefs = getSharedPreferences("DATE", MODE_PRIVATE);
                        String button_date = prefs.getString(rbdate[z], null);
                        String button_time = prefs.getString(rbtime[z], null);
                        int button_price = prefs.getInt(rbprice[z], 0);
                        String temp = "Date=" + button_date + "\n" + "Time=" + button_time + "\n" + "Price=" + button_price;
                        radioButton[z].setText(temp);
                        radioButton[z].setVisibility(View.VISIBLE);
                        Log.w("Radiobutton hidden"+radioButton[z], "radioButton[z].setVisibility(View.VISIBLE);");


//                        String temp = "Date="+button_date+"\n"+"Time="+button_time+"\n"+"Price="+button_price;
//                        radioButton[z].setText(temp);
//                        radioButton[z].setVisibility(View.VISIBLE);
//                        Log.w("Radiobutton hidden","radioButton[z].setVisibility(View.VISIBLE);");

                    }//here
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            try {
                //Convert the string-date into Date-date
                Date date = new SimpleDateFormat("dd-MM-yyyy").parse(restoredText);
                Log.w("Radiobutton hidden", "Date date=" + date);
                //increment the date by one
                Date dayAfter = new Date(date.getTime() + TimeUnit.DAYS.toMillis(1));
                Log.w("Radiobutton hidden", "Date dayAfter" + dayAfter);
                //convert Date-date back to String-date
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");//for testing
                restoredText = sdf.format(dayAfter);//for testing
                Log.w("Radiobutton hidden", "date" + dayAfter);
                SharedPreferences.Editor editor = getSharedPreferences("DATE", MODE_PRIVATE).edit();
                editor.putString("current_date", restoredText);
                editor.apply();
            } catch (java.text.ParseException e) {
                Log.w("Radiobutton hidden", "catch...");
                e.printStackTrace();
            }

        }//end of for


        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (scrollView.getScrollY() > previousScrollY && floatingActionButton.getVisibility() == View.VISIBLE) {
                    floatingActionButton.hide();
                } else if (scrollView.getScrollY() < previousScrollY && floatingActionButton.getVisibility() != View.VISIBLE) {
                    floatingActionButton.show();
                }
                previousScrollY = scrollView.getScrollY();
            }
        });
//    });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Please select one of the available dates...", Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences prefs = getSharedPreferences("DATE", MODE_PRIVATE);
//                    String button_date = prefs.getString(rbdate[z], null);
//                    String button_time = prefs.getString(rbtime[z], null);
//                    int button_price = prefs.getInt(rbprice[z], 0);
                    for(int i=0;i<7;i++){
                        if (radioButton[i].isChecked()){
                            String button_date = prefs.getString(rbdate[i], null);
                            String button_time = prefs.getString(rbtime[i], null);
                            int button_price = prefs.getInt(rbprice[i], 0);
                            Toast.makeText(getApplicationContext(),rbdate[i]+"->"+button_date+"\n"+rbtime[i]+"->"+button_time+"\n"+rbprice[i]+"->"+button_price , Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        // to hide the unedited radio buttons
        for(int a=0;a<7;a++) {
            String test ="RadioButton";
            if (test.equals(radioButton[a].getText())) {
                radioButton[a].setVisibility(View.GONE);
                Log.w("Radiobutton hidden", "radioButton[z].setVisibility(View.GONE);");
            }
            else{
                Log.w("Radiobutton hidden", "else part radioButton[z].setVisibility(View.GONE);");
            }
        }//end of loop
    }

}