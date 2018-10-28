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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class CardRecyclerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayout;
    ArrayList<CardClass> data;
    DatabaseReference mReference;
    FirebaseDatabase database;
    RecyclerView.Adapter mAdapter;
    Context mContext;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent intent = getIntent();
        final String string = intent.getStringExtra("key");
        TextView test;
        setContentView(R.layout.activity_card_recycler);
        test = (TextView)findViewById(R.id.textView2);
        test.setText(string);


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




        Query query = mReference.child("labs").child(string);                                                               //here
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
//                        Toast.makeText(mContext,"apple",Toast.LENGTH_SHORT).show();                                      //remove later
                    progressBar.setVisibility(View.GONE);
                    data.add(dataSnapshot1.getValue(CardClass.class));
                    mAdapter = new CardRecyclerAdapter(data,mContext,string);
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
