package com.example.lenovo_pc.aktu_lab;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    CardView clab,csmnr,cevnt;

    @Nullable
    Context context;

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getContext();
        clab=view.findViewById(R.id.cardview1);
        csmnr=view.findViewById(R.id.cardview2);
        cevnt=view.findViewById(R.id.cardview3);

        viewPager = (ViewPager) view.findViewById(R.id.viewPagerHome);

        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);

        ViewPagerAdapterHome viewPagerAdapter = new ViewPagerAdapterHome(context);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];


        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(context);
            dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        clab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LabcatFragment fragment =new LabcatFragment();
                LabcatFragmentNew fragment =new LabcatFragmentNew();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainframe, fragment,"fragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        csmnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog("Sorry no records found.");
            }
        });

        cevnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog("Sorry no records found.");
            }
        });
        // Inflate the layout for this fragment
        return view;


    }


    public void openDialog(String string) {

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

        // Set Custom Title

        TextView title = new TextView(context);
        // Title Properties
        title.setText("This section is yet to be added");
        title.setPadding(10, 10, 10, 10);   // Set Position
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);
        // Set Message
        TextView msg = new TextView(context);
        // Message Properties
        msg.setText(string);
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        alertDialog.setView(msg);
        // Set Button
        // you can more buttons
//        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"OK", new DialogInterface.OnClickListener() {
//
//            public void onClick(DialogInterface dialog, int which) {
//                // Perform Action on Button
//
//            }
//
//        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"CANCEL", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Perform Action on Button
            }

        });
        new Dialog(getActivity());
        alertDialog.show();
        // Set Properties for OK Button

        final Button okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        okBT.setPadding(50, 10, 10, 10);   // Set Position
        okBT.setTextColor(Color.BLUE);
        okBT.setLayoutParams(neutralBtnLP);

        final Button cancelBT = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        LinearLayout.LayoutParams negBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        negBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        cancelBT.setTextColor(Color.RED);
        cancelBT.setLayoutParams(negBtnLP);

    }
//    public class MyTimerTask extends TimerTask {
//
//        @Override
//        public void run() {
//
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//                    if(viewPager.getCurrentItem() == 0){
//                        viewPager.setCurrentItem(1);
//                    } else if(viewPager.getCurrentItem() == 1){
//                        viewPager.setCurrentItem(2);
//                    } else {
//                        viewPager.setCurrentItem(0);
//                    }
//
//                }
//            });
//
//        }
//    }

}
