package com.example.lenovo_pc.aktu_lab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button btnlab = (Button) view.findViewById(R.id.btn_labs3);
        btnlab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labcat fragment3 =new labcat();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainframe, fragment3,"fragment3");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
