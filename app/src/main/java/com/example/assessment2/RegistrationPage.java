/*Student Name: Angela Pellillo
 * Student ID: 21499500
 * Module: Mobile Web Application Development
 * Module ID: CP5CS93E*/

package com.example.assessment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class RegistrationPage extends AppCompatActivity {

    private Button cnlBtn, registerBtn;
    private TextInputLayout username_layout, full_name_layout, psw_layout, re_psw_layout, email_layout;
    private TextInputEditText username, password, re_password, full_name, email;
    private EditText dob,  country, address, city, postcode, acc_name, acc_num, acc_exp_date, acc_cvv_num, genderSpec;
    private RadioGroup gender_radio_group;
    private RadioButton gender_btn;
    private DatePickerDialog picker;
    ImageButton cvvInfo,pcInfo;


    private ArrayList<String> taken_usernames = new ArrayList<>();
    private static final String TAG="RegistrationPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);


        cnlBtn = findViewById(R.id.cnlBtn);
        registerBtn = findViewById(R.id.registerBtn);
        email_layout = findViewById(R.id.email_textInput_Layout);
        email = findViewById(R.id.email_EditText);
        country = findViewById(R.id.countryField);
        address = findViewById(R.id.addressField);
        city = findViewById(R.id.cityField);
        postcode = findViewById(R.id.postCodeField);
        cvvInfo = findViewById(R.id.cvvInfo);
        pcInfo = findViewById(R.id.pcInfo);
        acc_name = findViewById(R.id.nameCardField);
        acc_num = findViewById(R.id.cardNoField);
        acc_exp_date = findViewById(R.id.expiryDateField);
        acc_cvv_num = findViewById(R.id.cvvField);
        dob = findViewById(R.id.dobField);
        genderSpec = findViewById(R.id.genderSpec);


        username = findViewById(R.id.username_EditText);
        password = findViewById(R.id.password_EditText);
        re_password = findViewById(R.id.re_password_EditText);
        username_layout = findViewById(R.id.username_textInput_Layout);
        full_name_layout = findViewById(R.id.fullname_textInput_Layout);
        psw_layout = findViewById(R.id.password_textInput_Layout);
        re_psw_layout = findViewById(R.id.re_password_textInput_Layout);



        gender_radio_group = findViewById(R.id.gender_radioGroup);
        gender_radio_group.clearCheck();
        full_name = findViewById(R.id.fullname_EditText);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //Date Picker
                picker = new DatePickerDialog(RegistrationPage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dob.setText(dayOfMonth + "/" + (month + 1) +  "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });





        cnlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackLgn();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Register button clicked");

                final String user_name_str = username.getText().toString();
                checkUsernameAvailability(user_name_str);

            }
        });

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



    }

    private void toastMessage(int x){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, null);
        Toast toast = new Toast(RegistrationPage.this);
        TextView text = (TextView) layout.findViewById(R.id.toastMessage);
        text.setGravity(Gravity.RIGHT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 100, 100);
        if(x == 1){
            text.setText("A card security code is a series of numbers that, in addition to the bank card number," +
                    " is printed on a credit or debit card" );


        } else if (x == 2) {
            text.setText("A postal code is a series of letters or digits or both, sometimes including spaces or punctuation, " +
                    "included in a postal address for the purpose of sorting mail. " );

        }
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    //Method to pass the info to Firebase to register the user

    private void registerUser(String fullname, String username, String psw, String email, String textGender, String address, String city, String postcode,String country, String dob,
                              String acc_name, String acc_num, String acc_exp_date, String acc_cvv_num, String profilePicture) {
        FirebaseAuth authentication = FirebaseAuth.getInstance();

        //Create the user profile
        authentication.createUserWithEmailAndPassword(email, psw ).addOnCompleteListener(RegistrationPage.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser firebaseUser =  authentication.getCurrentUser();

                    //Storing user data into Firebase Database
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(username,fullname,textGender,address,city,postcode,country,dob,acc_name,acc_num,acc_exp_date,acc_cvv_num, profilePicture);

                    //Getting the user reference from the database for "Registered Users"
                    DatabaseReference refProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                    Log.d(TAG, "Before setValue call");
                    refProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "setValue success");
                                //Send Verification Email
                                firebaseUser.sendEmailVerification();

                                Toast.makeText(RegistrationPage.this, "User registered successfully. Please verify your email.",
                                        Toast.LENGTH_LONG).show();

                                //Go back to LoginPage if user registration is successful
                                Intent intent = new Intent(RegistrationPage.this, MainActivity.class);


                                startActivity(intent);
                                finish();

                            }
                            else{
                                Log.e(TAG, "Error storing user data", task.getException());
                                Log.e(TAG, "Exception message: " + task.getException().getMessage());

                                Toast.makeText(RegistrationPage.this, "Registration failed.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                else{
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e){
                        psw_layout.setError("Your password is too weak. Password must have more than 6 characters.");
                        psw_layout.requestFocus();
                    }
                    catch (FirebaseAuthUserCollisionException e){
                        email_layout.setError("Your email is already in use. Please enter a different email.");
                        email_layout.requestFocus();
                    }
                    catch (FirebaseAuthInvalidCredentialsException e){
                        email_layout.setError("Email invalid or already in use. Please re-enter.");
                        email_layout.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(RegistrationPage.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }


    private void goBackLgn(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    //Method to check if the username is already taken and for validation
    private void checkUsernameAvailability(final String username) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Registered Users");

        usersRef.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Username already taken, show an error message
                    Toast.makeText(RegistrationPage.this,"Username is already taken. Please choose a different one.",Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Username is already taken: " + username);
                } else {
                    // Username is unique, proceed with registration
                    int genderSelected = gender_radio_group.getCheckedRadioButtonId();
                    gender_btn = findViewById(genderSelected);
                    final TextInputLayout label;

                    String fullname_str = full_name.getText().toString();
                    String email_str = email.getText().toString();
                    String psw_str = password.getText().toString();
                    String re_psw_str = re_password.getText().toString();
                    String textGender;
                    String dob_str = dob.getText().toString();
                    String address_str = address.getText().toString();
                    String city_str = city.getText().toString();
                    String postcode_str = postcode.getText().toString();
                    String country_str = country.getText().toString();
                    String acc_name_str = acc_name.getText().toString();
                    String acc_num_str = acc_num.getText().toString();
                    String acc_exp_date_str = acc_exp_date.getText().toString();
                    String acc_cvv_num_str = acc_cvv_num.getText().toString();

                    if (TextUtils.isEmpty(fullname_str)) {
                        label = full_name_layout;
                        label.setError("Please enter full name");
                        full_name_layout.requestFocus();
                        label.setErrorEnabled(true);
                        return;
                    } else if (TextUtils.isEmpty(email_str)) {
                        label = email_layout;
                        label.setError("Please enter an email address.");
                        email_layout.requestFocus();
                        label.setErrorEnabled(true);
                        return;
                    } else if (TextUtils.isEmpty(psw_str)) {
                        label = psw_layout;
                        label.setError("Please enter a password");
                        psw_layout.requestFocus();
                        label.setErrorEnabled(true);
                        return;
                    } else if (TextUtils.isEmpty(re_psw_str)) {
                        label = re_psw_layout;
                        label.setError("Please confirm password.");
                        re_psw_layout.requestFocus();
                        label.setErrorEnabled(true);
                        return;
                    } else if (!psw_str.equals(re_psw_str)) {
                        label = re_psw_layout;
                        label.setError("Password not matching.");
                        label.setErrorEnabled(true);
                        return;
                    }

                    textGender = gender_btn.getText().toString();
                    if (textGender.equals("")) {
                        textGender = genderSpec.getText().toString();
                    }
                    String profilePicture = "";

                    registerUser(fullname_str, username, psw_str, email_str, textGender, address_str, city_str, postcode_str, country_str, dob_str, acc_name_str,
                            acc_num_str, acc_exp_date_str, acc_cvv_num_str, profilePicture);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error, if any
                Log.e(TAG, "Error checking username availability", databaseError.toException());
            }
        });
    }


}