package com.example.assessment2;
/*Student Name: Angela Pellillo
 * Student ID: 21499500
 * Module: Mobile Web Application Development
 * Module ID: CP5CS93E*/
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ResetPswPt1 extends AppCompatActivity {

   private EditText emailField;

    private Button cancelBtn, nextBtn;
    private FirebaseAuth authProfile;
    private ArrayList<String>code = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_psw_pt1);

        emailField = findViewById(R.id.emailField);
        cancelBtn =findViewById(R.id.cancelBtn);
        nextBtn = findViewById(R.id.nextBtn);


        code.add("1");
        code.add("2");
        code.add("3");
        code.add("4");

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                if(TextUtils.isEmpty(emailField.toString())){
                    Toast.makeText(ResetPswPt1.this, "Please enter registration email", Toast.LENGTH_SHORT).show();
                    emailField.setError("Email required.");
                    emailField.requestFocus();
                }else{
                    goNext(email);
                }


            }
        });


    }


    private void cancel(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void goNext(String email){
        authProfile = FirebaseAuth.getInstance();
        authProfile.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(ResetPswPt1.this, ResetPswPt2.class);
                    intent.putExtra("verificationCode", code);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }

            }
        });

    }


}