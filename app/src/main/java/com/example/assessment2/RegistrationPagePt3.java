package com.example.assessment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationPagePt3 extends AppCompatActivity {

    Button backBtn, registerBtn;
    ImageButton cvvInfo,pcInfo;
    private RegistrationHandler registrationHandler;
    EditText acc_name, acc_num, acc_exp_date, acc_cvv_num;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page_pt3);

        backBtn = findViewById(R.id.backBtn);
        registerBtn = findViewById(R.id.registerBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        cvvInfo = findViewById(R.id.cvvInfo);
        pcInfo = findViewById(R.id.pcInfo);
        acc_name = findViewById(R.id.nameCardField);
        acc_num = findViewById(R.id.cardNoField);
        acc_exp_date = findViewById(R.id.expiryDateField);
        acc_cvv_num = findViewById(R.id.cvvField);


        cvvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage(1);
            }
        });

        pcInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage(2);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });



    }

    private void goBack(){
        Intent intent = new Intent(this, RegistrationPagePt2.class);
        startActivity(intent);
    }

    private void register(){
        String user_name = getIntent().getExtras().getString("user_name");
        String fullname = getIntent().getExtras().getString("fullname");
        String psw = getIntent().getExtras().getString("psw");
        String textGender = getIntent().getExtras().getString("textGender");
        String email_str = getIntent().getExtras().getString("email");
        String address_str = getIntent().getExtras().getString("address");
        String city_str = getIntent().getExtras().getString("city");
        String postcode_str = getIntent().getExtras().getString("postcode");
        String dob_str = getIntent().getExtras().getString("dob");
        String country_str = getIntent().getExtras().getString("country");

        String acc_name_str = acc_name.getText().toString();
        String acc_num_str = acc_name.getText().toString();
        String acc_exp_date_str = acc_exp_date.getText().toString();
        String acc_cvv_num_str = acc_cvv_num.getText().toString();

        registerUser(fullname,user_name,psw,email_str,textGender,address_str,city_str,postcode_str,country_str, dob_str, acc_name_str,
                acc_num_str,acc_exp_date_str,acc_cvv_num_str);

    }


    private void toastMessage(int x){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, null);
        TextView text = (TextView) layout.findViewById(R.id.toastMessage);
        text.setGravity(Gravity.RIGHT);
        if(x == 1){
            text.setText("A card security code is a series of numbers that, in addition to the bank card number," +
                    " is printed on a credit or debit card" );
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 100, -300);

        } else if (x == 2) {
            text.setText("A postal code is a series of letters or digits or both, sometimes including spaces or punctuation, " +
                    "included in a postal address for the purpose of sorting mail. " );
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 100, 100);
        }

    }

    //Method to pass the info collected during the first registration page to Firebase.
    // To be put on RegistrationPage3, and all the info below passed as extra.

    private void registerUser(String fullname, String userName, String psw, String email, String textGender, String address, String city, String postcode,String country, String dob,
                              String acc_name, String acc_num, String acc_exp_date, String acc_cvv_num) {
        FirebaseAuth authentication = FirebaseAuth.getInstance();

        //Create the user profile
        authentication.createUserWithEmailAndPassword(email, psw ).addOnCompleteListener(RegistrationPagePt3.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(RegistrationPagePt3.this, "User Registered", Toast.LENGTH_LONG).show();
                    FirebaseUser firebaseUser =  authentication.getCurrentUser();

                    //Storing user data into Firebase Database
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(userName,fullname,textGender,address,city,postcode,country,dob, acc_name,
                            acc_num, acc_exp_date,acc_cvv_num);

                    //Getting the user reference from the database for "Registered Users"
                    DatabaseReference refProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                    refProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                //Send Verification Email
                                firebaseUser.sendEmailVerification();

                                Toast.makeText(RegistrationPagePt3.this, "User registered successfully. Please verify your email.",
                                        Toast.LENGTH_LONG).show();

                                //Open user profile after successful registration
                                Intent intent = new Intent(RegistrationPagePt3.this, UserProfileActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else{
                                Toast.makeText(RegistrationPagePt3.this, "Registation failed.",
                                        Toast.LENGTH_LONG).show();

                            }

                        }
                    });

                }
                /*else{
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthUserCollisionException e){

                    }
                }*/
            }
        });

    }

}