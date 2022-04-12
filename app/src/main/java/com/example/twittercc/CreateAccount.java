package com.example.twittercc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class CreateAccount extends AppCompatActivity {
    ImageView Back;
    Button Next;
    String dob;
    Calendar calendar;
    EditText Name, Email,DOB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Next = findViewById(R.id.next_btn);
        Back = findViewById(R.id.back_btn);
        Name = findViewById(R.id.Name_edt);
        Email = findViewById(R.id.PhnOrEmail_edt);
        DOB = findViewById(R.id.Dob_edt);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
       calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                updateDob();
            }
        };
        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CreateAccount.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate(Name.getText().toString(),Email.getText().toString(),DOB.getText().toString())) {
                    Intent intent = new Intent(CreateAccount.this, SignIn.class);
                    intent.putExtra("name",Name.getText().toString());
                    intent.putExtra("email",Email.getText().toString());
                    intent.putExtra("dob",dob);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(CreateAccount.this, "Please fill in all details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean validate(String name, String email,String dob)
    {
        if(name.equals("") || email.equals("")|| dob.equals(""))
            return false;
            return true;

    }
    public void updateDob(){
        String dateformat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateformat, Locale.US);
        DOB.setText(simpleDateFormat.format(calendar.getTime()));
        dob = simpleDateFormat.format(calendar.getTime());
    }
}