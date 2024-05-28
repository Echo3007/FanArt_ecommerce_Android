package com.example.assessment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ResetPswPt3 extends AppCompatActivity {

    private Button resetBtn, cnlBtn;
    private TextInputEditText new_pswField, re_pswField;
    private TextInputLayout new_pswLayout, re_pswLayout;
    private String email;
    private DatabaseReference reference = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_psw_pt3);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("email")) {
            email = intent.getStringExtra("email");
        }

        new_pswField = findViewById(R.id.newPswField);
        re_pswField = findViewById(R.id.re_PswField);
        new_pswLayout = findViewById(R.id.newPsw);
        re_pswLayout = findViewById(R.id.re_Psw);


        resetBtn = findViewById(R.id.resetBtn);
        cnlBtn = findViewById(R.id.cnlBtn);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psw_reset();

            }
        });

        cnlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

    }

    private void cancel() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void psw_reset() {
        String new_psw_text = new_pswField.getText().toString();
        String re_new_psw = re_pswField.getText().toString();

        // Check if passwords match
        if (!new_psw_text.equals(re_new_psw)) {
            re_pswLayout.setError("Password not matching.");
            re_pswLayout.requestFocus();
        } else {
            // Fetch user UID by email
            DatabaseReference usersRef = FirebaseDatabase.getInstance("https://assessment2-4390a-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Registered Users");

            usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String userUid = snapshot.getKey();
                        updatePasswordUsingUid(userUid, new_psw_text);
                        return;
                    }

                    // Handle the case where no user with the specified email was found
                    Toast.makeText(ResetPswPt3.this, "User not found with the specified email", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                    Toast.makeText(ResetPswPt3.this, "Error retrieving user data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updatePasswordUsingUid(String userUid, String newPassword) {
        FirebaseAuth authentication = FirebaseAuth.getInstance();
        authentication.signInWithCustomToken(userUid)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = authentication.getCurrentUser();
                        if (user != null) {
                            user.updatePassword(newPassword)
                                    .addOnCompleteListener(passwordUpdateTask -> {
                                        if (passwordUpdateTask.isSuccessful()) {
                                            // Password updated successfully
                                            Toast.makeText(ResetPswPt3.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                            // Add any additional logic you need after successful password update
                                        } else {
                                            // Handle password update failure
                                            Toast.makeText(ResetPswPt3.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        // Handle sign-in failure
                        Toast.makeText(ResetPswPt3.this, "Failed to sign in with custom token", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}