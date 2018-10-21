package com.example.lenovo_pc.aktu_lab;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class LabcatFragment extends Fragment {


    public LabcatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_labcat, container, false);
        Button button1 =(Button)view.findViewById(R.id.button6);
        Button button2 =(Button)view.findViewById(R.id.button8);
        Button button3 =(Button)view.findViewById(R.id.button9);
        Button button4 =(Button)view.findViewById(R.id.button10);
        Button button5 =(Button)view.findViewById(R.id.button11);
        Button button6 =(Button)view.findViewById(R.id.button12);
        Button button7 =(Button)view.findViewById(R.id.button13);
        Button button8 =(Button)view.findViewById(R.id.button14);
        Button button9 =(Button)view.findViewById(R.id.button15);
        Button button10 =(Button)view.findViewById(R.id.button16);
        Button button11 =(Button)view.findViewById(R.id.button17);
        Button button12 =(Button)view.findViewById(R.id.button18);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "Computer Science";
                nextActivity(string);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "Electronics";
                nextActivity(string);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "Electrical";
                nextActivity(string);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "Mechanical";
                nextActivity(string);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "Information Technology";
                nextActivity(string);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "Civil";
                nextActivity(string);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "Aeronautical";
                nextActivity(string);
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "Automobile";
                nextActivity(string);
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "Food";
                nextActivity(string);
            }
        });
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "Paint";
                nextActivity(string);
            }
        });
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "Plastic";
                nextActivity(string);
            }
        });
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "Textile";
                nextActivity(string);
            }
        });

        return view;
    }
    public void nextActivity(String string){
        Intent  intent= new Intent(getActivity(),CardRecyclerActivity.class);
        intent.putExtra("key",string);
        startActivity(intent);
    }

}
