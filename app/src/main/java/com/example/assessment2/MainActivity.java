/*Student Name: Angela Pellillo
 * Student ID: 21499500
 * Module: Mobile Web Application Development
 * Module ID: CP5CS93E*/

package com.example.assessment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private TextView signUp, pswReset;
    private Button login;
    private EditText email, psw;
    private FirebaseAuth authProfile;
    private static String TAG = "LOGIN";
    private DatabaseReference reference = null;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        email = findViewById(R.id.emailSignIn);
        psw = findViewById(R.id.pswSignIn);
        authProfile = FirebaseAuth.getInstance();

        //Login User
        login = findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = email.getText().toString();
                String textPsw = psw.getText().toString();

                if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(MainActivity.this,"Please enter email address",Toast.LENGTH_SHORT).show();
                    email.setError("Email is required");
                    email.requestFocus();
                } else if (TextUtils.isEmpty(textPsw)) {
                    Toast.makeText(MainActivity.this, "Please enter password",Toast.LENGTH_SHORT).show();
                    psw.setError("Password is required");
                    psw.requestFocus();
                }else{
                    logIn(textEmail, textPsw);

                }
            }
        });


        signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpPage();
            }
        });


        pswReset = findViewById(R.id.pswReset);
        pswReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPsw();
            }
        });
    }


    private void signUpPage(){
        Intent intent = new Intent(this, RegistrationPage.class);
        startActivity(intent);
    }

    private void logIn(String textEmail, String textPsw){
        authProfile.signInWithEmailAndPassword(textEmail,textPsw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String userId = authProfile.getCurrentUser().getUid();
                    retrieveUsernameFromDatabase(userId);
                    Toast.makeText(MainActivity.this,"Login successful",Toast.LENGTH_SHORT).show();

                }
                else{
                    try{
                        throw  task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        email.setError("Invalid credentials. Please try again.");
                        email.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        email.setError("Invalid credentials. Please try again.");
                        email.requestFocus();
                    } catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(MainActivity.this,"Something went wrong. Please try again.",Toast.LENGTH_SHORT).show();
                }

            }

        });

    }



    private void retrieveUsernameFromDatabase(String userId) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Registered Users");

        usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        String username = user.getUsername();
                        // Now you have the correct username, you can proceed as needed
                        // For example, start the Home Activity with the username
                        startHomeActivity(username);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    private void startHomeActivity(String username) {
        Intent intent = new Intent(MainActivity.this, HomePage.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }



    private void resetPsw(){
        Intent intent = new Intent(this, ResetPswPt1.class);
        startActivity(intent);
    }

}