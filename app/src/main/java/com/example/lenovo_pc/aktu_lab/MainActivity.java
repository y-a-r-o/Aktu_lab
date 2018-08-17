package com.example.lenovo_pc.aktu_lab;

import android.content.res.ColorStateList;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigation;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
      //  FragmentManager fragmentManager = getSupportFragmentManager();
      //  FragmentTransaction transaction = fragmentManager.beginTransaction();

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_home:
                    setTitle("Home");
                    HomeFragment fragment =new HomeFragment();
                    item.setChecked(true);
                    FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mainframe,fragment,"Fragment");
                    fragmentTransaction.commit();
        //           transaction.replace(R.id.mainframe,new HomeFragment()).commit();
                    break;
                case R.id.navigation_search:
                    setTitle("Search");
                    item.setChecked(true);

                    SearchFragment fragment2 =new SearchFragment();
                    FragmentTransaction fragmentTransaction2= getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.mainframe,fragment2,"Fragment");
                    fragmentTransaction2.commit();
                    // transaction.replace(R.id.mainframe,new Sear chFragment()).commit();
                    break;
                case R.id.navigation_myaccount:
                    setTitle("My Account");
                    item.setChecked(true);

                    MyAccountFragment fragment3 =new MyAccountFragment();
                    FragmentTransaction fragmentTransaction3= getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.mainframe,fragment3,"Fragment");
                    fragmentTransaction3.commit();
          //          transaction.replace(R.id.mainframe,new MyAccountFragment()).commit();
                    break;
            }
            return false;
        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
      //  FragmentManager fragmentManager = getSupportFragmentManager();
      //  FragmentTransaction transaction=fragmentManager.beginTransaction();
      //  transaction.replace(R.id.navigation,new HomeFragment()).commit();
        setTitle("Home");
//        navigation.setBackgroundColor();
        HomeFragment fragment =new HomeFragment();
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainframe,fragment,"Fragment");
        fragmentTransaction.commit();

    }

}
