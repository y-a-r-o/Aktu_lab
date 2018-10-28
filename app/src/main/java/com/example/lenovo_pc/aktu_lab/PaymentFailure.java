package com.example.lenovo_pc.aktu_lab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PaymentFailure extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_failure);
        TextView t1,t2;
        t1=findViewById(R.id.payfailure);
        t2=findViewById(R.id.info);
    }
}
