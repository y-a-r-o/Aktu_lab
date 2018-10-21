package com.example.lenovo_pc.aktu_lab;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LabDetailsActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Context mContext;
    ProgressBar progressBar;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_details);
        final int key = getIntent().getIntExtra("key", 0);
        final String category = getIntent().getStringExtra("category");
//        Toast.makeText(getApplicationContext(), "category=" + category + " key=" + key, Toast.LENGTH_SHORT).show();

        mContext = getApplicationContext();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        name = (TextView) findViewById(R.id.name1);
        collagename = (TextView) findViewById(R.id.collagename1);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);    //for dots
        description = (TextView) findViewById(R.id.description);
        address = (TextView) findViewById(R.id.address);

        scrollView = (ScrollView)findViewById(R.id.scrollview);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.bookbtn);
        previousScrollY = scrollView.getScrollY();

//        Query query = databaseReference.child("labs").child(category).child(""+key);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                details=dataSnapshot.getValue(LabDetailsClass.class);
//                progressBar.setVisibility(View.GONE);
//                String[] mImages = {details.getImage1(),details.getImage2(),details.getImage3(),details.getCardimage()};
//                viewPagerAdapter = new ViewPagerAdapter(mContext,mImages);
//                viewPager.setAdapter(viewPagerAdapter);
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(mContext,"Database Error Occured...",Toast.LENGTH_SHORT).show();
//            }

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                details = dataSnapshot.child("labs").child(category).child("" + key).getValue(LabDetailsClass.class);
                progressBar.setVisibility(View.GONE);

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
                for(int i = 0; i < dotscount; i++){
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
                        for(int i = 0; i< dotscount; i++){
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
        };
        databaseReference.addValueEventListener(postListener);
        //upto here for dots
        //for getting date from internet

//        DatabaseReference timereference = FirebaseDatabase.getInstance().getReference();
//        Map<String,Object> value = new HashMap<>();
//        value.put("timestamp",ServerValue.TIMESTAMP);
//        Toast.makeText(getApplicationContext(),""+ServerValue.TIMESTAMP,Toast.LENGTH_LONG).show();


        //upto here is for time

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

    }
}