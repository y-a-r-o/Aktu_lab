package com.example.lenovo_pc.aktu_lab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import org.apache.commons.net.time.TimeTCPClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigation;
    FrameLayout mainframe;
    HomeFragment fragment;
    SearchFragment fragment2;
    MyAccountFragment fragment3;
    String date_string;

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
//        getDatebyApoorvandYash();                //to get date
        getDatebyApoorvandYash();                //to get date
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




    public void getDatebyApoorvandYash() {
//        String date_string;
        try {
            //set time in mili
            Thread.sleep(3000);

        }catch (Exception e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    try {
                        TimeTCPClient client = new TimeTCPClient();
                        try {

                            // Set timeout of 60 seconds
                            client.setDefaultTimeout(60000);
                            // Connecting to time server
                            // Other time servers can be found at : http://tf.nist.gov/tf-cgi/servers.cgi#
                            // Make sure that your program NEVER queries a server more frequently than once every 4 seconds
                            client.connect("time.nist.gov");
                            Date date =client.getDate();
                            Log.w("Date:",""+date);
//                            date = new SimpleDateFormat("dd-MM-YYYY").parse();
                            SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-YYYY");
                            date_string = sdf.format(date);
                            SharedPreferences.Editor editor=getSharedPreferences("Date",MODE_PRIVATE).edit();
                            editor.putString("current_date",date_string);
                            editor.apply();
                            Log.w("Date:",""+date_string);
                        } finally {
                            Log.w("Date:","finally");
                            client.disconnect();
                        }
                    } catch (IOException e) {
                        Log.w("Date:","2nd try");
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    Log.w("Date:","1st try");
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }
}
