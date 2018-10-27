package com.example.lenovo_pc.aktu_lab;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyBookingRecyclerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayout;
    ArrayList<MyBookingCardClass> data;
    DatabaseReference mReference;
    FirebaseDatabase database;
    RecyclerView.Adapter mAdapter;
    Context mContext;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView test;
        setContentView(R.layout.activity_card_recycler);
        test = (TextView)findViewById(R.id.textView2);
        test.setText("My Bookings");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mContext=getApplicationContext();
        database=FirebaseDatabase.getInstance();
        mReference=database.getReference();
        mReference.keepSynced(true);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mLayout= new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayout);
        data = new ArrayList<>();



        firebaseAuth= FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        Query query = mReference.child("users").child(uid).child("mybookings");                                                               //here
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    progressBar.setVisibility(View.GONE);
                    data.add(dataSnapshot1.getValue(MyBookingCardClass.class));
                    mAdapter = new MyBookingRecyclerAdapter(data,mContext,"mybookings");
                    recyclerView.setAdapter(mAdapter);
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext,"error",Toast.LENGTH_SHORT).show();                                        //remove later
            }
        });

    }

}
