package com.example.lenovo_pc.aktu_lab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    CardView clab,csmnr,cevnt;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        clab=view.findViewById(R.id.cardview1);
        csmnr=view.findViewById(R.id.cardview2);
        cevnt=view.findViewById(R.id.cardview3);
        clab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LabcatFragment fragment =new LabcatFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainframe, fragment,"fragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
