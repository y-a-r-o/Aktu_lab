package com.example.lenovo_pc.aktu_lab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class PersonalDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    Button button;
    EditText editTextName;
    EditText edittextRollNo;
    EditText edittextPhone;
    EditText editTextCollege;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        Objects.requireNonNull(getSupportActionBar()).hide();

        editTextName=findViewById(R.id.Name);
        edittextRollNo=findViewById(R.id.rollno);
        edittextPhone=findViewById(R.id.Phoneno);
        editTextCollege=findViewById(R.id.collegename1);
        button = findViewById(R.id.paynow);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.colleges, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.getSelectedItem().toString();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextName.getText().toString().equals("") || edittextRollNo.getText().toString().equals("") || editTextCollege.getText().toString().equals("") || edittextPhone.getText().toString().equals("") || spinner.getSelectedItem().toString().equals("Select Course")) {
                    Toast.makeText(getApplicationContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    SharedPreferences.Editor editor = getSharedPreferences("Personal Details", MODE_PRIVATE).edit();
                    editor.putString("Name", editTextName.getText().toString());
                    editor.putString("RollNo", edittextRollNo.getText().toString());
                    editor.putString("College", editTextCollege.getText().toString());
                    editor.putString("Phone", edittextPhone.getText().toString());
                    editor.apply();

                    Intent intent = new Intent(PersonalDetailsActivity.this, Payments.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences.Editor editor=getSharedPreferences("Personal Details",MODE_PRIVATE).edit();
        editor.putString("Course",parent.getItemAtPosition(position).toString());
        editor.apply();
        String text = parent.getItemAtPosition(position).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}