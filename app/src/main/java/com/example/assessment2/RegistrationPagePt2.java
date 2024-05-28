package com.example.assessment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationPagePt2 extends AppCompatActivity {

    Button backBtn, nextBtn;
    private EditText country, address, city, postcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page_pt2);

        backBtn = findViewById(R.id.backBtn);
        nextBtn = findViewById(R.id.nextBtn);
        country = findViewById(R.id.countryField);
        address = findViewById(R.id.addressField);
        city = findViewById(R.id.cityField);
        postcode = findViewById(R.id.postCodeField);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext();
            }
        });

    }

    private void goBack(){
        Intent intent = new Intent(this, RegistrationPage.class);
        startActivity(intent);

    }

    private void goNext(){
        Intent intent = new Intent(this, RegistrationPagePt3.class);

        String user_name = getIntent().getExtras().getString("user_name");
        String fullname = getIntent().getExtras().getString("fullname");
        String psw = getIntent().getExtras().getString("psw");
        String textGender = getIntent().getExtras().getString("textGender");
        String email_str = getIntent().getExtras().getString("email");
        String dob_str = getIntent().getExtras().getString("dob");
        String country_str = country.getText().toString();
        String address_str = address.getText().toString();
        String city_str = city.getText().toString();
        String postcode_str = postcode.getText().toString();


        intent.putExtra("user_name",user_name);
        intent.putExtra("fullname",fullname);
        intent.putExtra("psw",psw);
        intent.putExtra("textGender",textGender);
        intent.putExtra("address",address_str);
        intent.putExtra("city",city_str);
        intent.putExtra("postcode",postcode_str);
        intent.putExtra("dob",dob_str);
        intent.putExtra("email", email_str);
        intent.putExtra("country", country_str);

        startActivity(intent);

    }


}