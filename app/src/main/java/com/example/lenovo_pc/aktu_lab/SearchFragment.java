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
    Button button;
    CardClass temp = new CardClass();
    TextView textView;
    int itemcount;
    int flag1;
    int flag2;

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
        button = (Button) view.findViewById(R.id.searchbutton);
        textView = (TextView) view.findViewById(R.id.emptytextview);
        textView.setVisibility(View.GONE);

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
                String string=editText.getText().toString();                 //to string
                recyclerView.removeAllViewsInLayout();                       //to empty the recycler view
                data.clear();                                                //to stop showing previos data
                textView.setVisibility(View.GONE);
                flag1=0;
                flag2=0;

                if(TextUtils.isEmpty(string)){
                    editText.setError("Please enter anything to search!!!");
                }
                else{
//                    Toast.makeText(mContext,"Searching...",Toast.LENGTH_LONG).show();

                    for(int i=0;i<itemcount;i++){
                        obj=arrayList.get(i);
                        final String s = obj.getTitle();
                        Query query = mReference.child("labs").child(s).orderByChild("name").startAt(string).endAt(string+"\uf8ff");
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        data.add(dataSnapshot1.getValue(CardClass.class));
                                        mAdapter = new CardRecyclerAdapter(data, mContext, s);
                                        recyclerView.setAdapter(mAdapter);

                                    }
                                }
                                else{
                                    flag1=1;
                                    if(flag2==1){
                                        textView.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    for(int i=0;i<itemcount;i++){
                        obj=arrayList.get(i);
                        final String s = obj.getTitle();
                        Query query = mReference.child("labs").child(s).orderByChild("collage_name").startAt(string).endAt(string+"\uf8ff");
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        data.add(dataSnapshot1.getValue(CardClass.class));
                                        mAdapter = new CardRecyclerAdapter(data, mContext, s);
                                        recyclerView.setAdapter(mAdapter);
                                    }
                                }
                                else{
                                    flag2=1;
                                    if(flag1==1){
                                        textView.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
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
