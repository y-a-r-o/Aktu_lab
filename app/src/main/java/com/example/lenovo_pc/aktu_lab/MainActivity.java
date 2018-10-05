package com.example.lenovo_pc.aktu_lab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigation;
    FrameLayout mainframe;
    HomeFragment fragment;
    SearchFragment fragment2;
    MyAccountFragment fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.navigation);
        mainframe = findViewById(R.id.mainframe);
        fragment=new HomeFragment();
        fragment2=new SearchFragment();
        fragment3=new MyAccountFragment();
       setFragment(fragment);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        setFragment(fragment);
                        return true;
                    case R.id.navigation_search:
                        setFragment(fragment2);
                        return true;
                    case R.id.navigation_myaccount:
                        setFragment(fragment3);
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainframe, fragment);
        fragmentTransaction.commit();
    }

}
