package com.example.lenovo_pc.aktu_lab;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayout;
    ArrayList<CardClass> data;
    ArrayList<LabcatClass> arrayList;
    LabcatClass obj;
    DatabaseReference mReference;
    FirebaseDatabase database;
    RecyclerView.Adapter mAdapter;
    Context mContext;
    EditText editText;
    ImageButton button;
    CardClass temp = new CardClass();
    TextView textView;
    int itemcount;
//    int flag1;
//    int flag2;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mContext=getContext();
        database=FirebaseDatabase.getInstance();
        mReference=database.getReference();
        mReference.keepSynced(true);
        recyclerView = (RecyclerView) view.findViewById(R.id.searchrecyclerview);
        recyclerView.setHasFixedSize(true);
        mLayout= new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayout);
        data = new ArrayList<>();
        arrayList = new ArrayList<>();
        obj = new LabcatClass();
        temp = new CardClass();
        editText = (EditText)view.findViewById(R.id.searcheditText);
        button = (ImageButton) view.findViewById(R.id.searchbutton);
        textView = (TextView) view.findViewById(R.id.emptytextview);
        textView.setText("Enter any name of any lab, workshop, seminar or search through college name.");

        Query query = mReference.child("category_labs");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    arrayList.add(dataSnapshot1.getValue(LabcatClass.class));
                    itemcount = arrayList.size();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setVisibility(View.GONE);
                String string=editText.getText().toString();                 //to string
                recyclerView.removeAllViewsInLayout();                       //to empty the recycler view
                data.clear();                                                //to stop showing previos data
                textView.setVisibility(View.GONE);
//                flag1=0;
//                flag2=0;

                if(TextUtils.isEmpty(string)){
                    editText.setError("Please enter anything to search!!!");
                    textView.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(mContext, "Searching...", Toast.LENGTH_LONG).show();
                    for (int j=0;j<=3;j++) {
                        if (j==0){

                        }
                        else if(j==1){
                            string=string.toUpperCase();
                        }
                        else if(j==2){
                            string=string.toLowerCase();
                        }
                        else{
                            boolean space = true;
                            StringBuilder builder = new StringBuilder(string);
                            final int len = builder.length();
                            for (int i = 0; i < len; ++i) {
                            char c = builder.charAt(i);
                            if (space) {
                                if (!Character.isWhitespace(c)) {
                                // Convert to title case and switch out of whitespace mode.
                                    builder.setCharAt(i, Character.toTitleCase(c));
                                    space = false;
                                }
                            } else if (Character.isWhitespace(c)) {
                                 space = true;
                            } else {
                                  builder.setCharAt(i, Character.toLowerCase(c));
                            }
                            }
                            string = new String(builder);
                        }

                        for (int i = 0; i < itemcount; i++) {
                            obj = arrayList.get(i);
                            final String s = obj.getTitle();
                            Query query = mReference.child("labs").child(s).orderByChild("name").startAt(string).endAt(string + "\uf8ff");
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            data.add(dataSnapshot1.getValue(CardClass.class));
                                            mAdapter = new CardRecyclerAdapter(data, mContext, s);
                                            recyclerView.setAdapter(mAdapter);
                                        }
                                    } else {
//                                    flag1=1;
//                                    if(flag2==1){
//                                        textView.setVisibility(View.VISIBLE);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                        for (int i = 0; i < itemcount; i++) {
                            obj = arrayList.get(i);
                            final String s = obj.getTitle();
                            Query query = mReference.child("labs").child(s).orderByChild("collage_name").startAt(string).endAt(string + "\uf8ff");
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            data.add(dataSnapshot1.getValue(CardClass.class));
                                            mAdapter = new CardRecyclerAdapter(data, mContext, s);
                                            recyclerView.setAdapter(mAdapter);
                                        }
                                    } else {
//                                    flag2=1;
//                                    if(flag1==1){
//                                        textView.setVisibility(View.VISIBLE);
//                                    }
                                        if (data.isEmpty()) {
                                            textView.setText("Sorry!!! No records founds...");
                                            textView.setVisibility(View.VISIBLE);
                                        } else {
                                            textView.setVisibility(View.GONE);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        }
                    }
                }
                //here
//                if(data.isEmpty()){                 //not working maybe this is called before data is loaded into the recyclerview
//                    textView.setVisibility(View.VISIBLE);
//                }

            }
        });



        return view;
    }

}
