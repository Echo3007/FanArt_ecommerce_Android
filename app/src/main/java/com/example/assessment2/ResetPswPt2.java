/*Student Name: Angela Pellillo
 * Student ID: 21499500
 * Module: Mobile Web Application Development
 * Module ID: CP5CS93E*/
package com.example.assessment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ResetPswPt2 extends AppCompatActivity {
    private TextView counter;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 60000; // 60 seconds

    private Button backBtn, nextBtn;
    private EditText num1, num2, num3, num4;
    private ArrayList<String> verificationCode = new ArrayList<>();
    ArrayList<String> inserted_code = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_psw_pt2);


        backBtn = findViewById(R.id.backBtn);
        nextBtn = findViewById(R.id.nextBtn);
        verificationCode = getIntent().getStringArrayListExtra("verificationCode");

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);




        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserted_code.add(num1.getText().toString());
                inserted_code.add(num2.getText().toString());
                inserted_code.add(num3.getText().toString());
                inserted_code.add(num4.getText().toString());
                Toast.makeText(ResetPswPt2.this, "Email sent to reset passsword", Toast.LENGTH_SHORT).show();

                boolean codesMatch = true;

                for (int i = 0; i < verificationCode.size(); i++) {
                    if (!verificationCode.get(i).equals(inserted_code.get(i))) {
                        codesMatch = false;
                        break;  // No need to continue checking if a mismatch is found
                    }
                }
                if (codesMatch) {
                    // The codes match, proceed to the next step
                    goNext();
                }

            }
        });


        counter = findViewById(R.id.counter);

        // Initialize the countdown timer
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                // Countdown finished, you can handle any additional logic here
                counter.setText("Code has expired");
            }
        };

        // Start the countdown timer
        countDownTimer.start();
    }


    private void goBack(){
        Intent intent = new Intent(this, ResetPswPt1.class);
        startActivity(intent);

    }

    private void goNext(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    private void updateCountdownText() {
        int seconds = (int) (timeLeftInMillis / 1000);
        String timeLeftFormatted = String.format("The code is active for %02d seconds", seconds);
        counter.setText(timeLeftFormatted);
    }
}
