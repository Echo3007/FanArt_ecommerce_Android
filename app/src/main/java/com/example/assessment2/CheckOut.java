package com.example.assessment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckOut extends AppCompatActivity {

    private TextView orderTotalTextView, address, orderTotalText2;
    private TextView paymentMethodTextView;
    private float total;
    private String paymentMethod,addressDelivery, deliveryOption;
    private RadioGroup paymentRadioGroup, addressRadioGroup, deliveryRadioButton;
    private ImageButton cvvInfo, pcInfo;
    private Button cancelBtn, placeOrder;
    private  String new_addressDelivery, new_payment_method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_check_out);


        /*Bundle args = getArguments();
        if (args != null) {
            float total = args.getFloat("totalAmount", 0.0f);
            // Use totalAmount as needed
        }*/

        orderTotalTextView = findViewById(R.id.orderTotal);
        paymentMethodTextView = findViewById(R.id.paymentMethod);
        //total = getIntent().getExtras().getFloat("total");
        paymentRadioGroup = findViewById(R.id.paymentRadioGroup);
        addressRadioGroup = findViewById(R.id.addressRadioGroup);
        cvvInfo = findViewById(R.id.cvvInfo);
        pcInfo = findViewById(R.id.pcInfo);
        address = findViewById(R.id.address);
        deliveryRadioButton = findViewById(R.id.deliveryRadioGroup);
        orderTotalText2 = findViewById(R.id.orderTotalText2);



        cancelBtn = findViewById(R.id.cancelBtn);
        placeOrder = findViewById(R.id.placeOrder);

        //EditText are made invisible at first
        findViewById(R.id.nameCardField).setVisibility(View.GONE);
        findViewById(R.id.cardNoField).setVisibility(View.GONE);
        findViewById(R.id.expiryDateField).setVisibility(View.GONE);
        findViewById(R.id.cvvField).setVisibility(View.GONE);
        findViewById(R.id.addressField).setVisibility(View.GONE);
        findViewById(R.id.cityField).setVisibility(View.GONE);
        findViewById(R.id.postCodeField).setVisibility(View.GONE);
        findViewById(R.id.countryField).setVisibility(View.GONE);
        findViewById(R.id.cvvInfo).setVisibility(View.GONE);
        findViewById(R.id.pcInfo).setVisibility(View.GONE);

        pcInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage(2);
            }
        });

        cvvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage(1);
            }
        });

// OnCheckedChangeListener for Payment RadioGroup
        paymentRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.otherPayment) {
                    // Show relevant EditText views for payment
                    findViewById(R.id.nameCardField).setVisibility(View.VISIBLE);
                    findViewById(R.id.cardNoField).setVisibility(View.VISIBLE);
                    findViewById(R.id.expiryDateField).setVisibility(View.VISIBLE);
                    findViewById(R.id.cvvField).setVisibility(View.VISIBLE);
                    findViewById(R.id.cvvInfo).setVisibility(View.VISIBLE);

                    String userName = ((EditText) findViewById(R.id.nameCardField)).getText().toString();
                    String userCardNum = ((EditText) findViewById(R.id.cardNoField)).getText().toString();
                    String userExpDate = ((EditText) findViewById(R.id.expiryDateField)).getText().toString();


                    // Combine the user-input address details
                    new_addressDelivery = userName + ",\n" + userCardNum + ",\n" + userExpDate;



                } else {
                    // Hide relevant EditText views for payment
                    findViewById(R.id.nameCardField).setVisibility(View.GONE);
                    findViewById(R.id.cardNoField).setVisibility(View.GONE);
                    findViewById(R.id.expiryDateField).setVisibility(View.GONE);
                    findViewById(R.id.cvvField).setVisibility(View.GONE);
                    findViewById(R.id.cvvInfo).setVisibility(View.GONE);
                    new_payment_method = paymentMethod;
                }
            }
        });

//  OnCheckedChangeListener for Address RadioGroup
        addressRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.otherAddress) {
                    // Show relevant EditText views for address
                    findViewById(R.id.addressField).setVisibility(View.VISIBLE);
                    findViewById(R.id.cityField).setVisibility(View.VISIBLE);
                    findViewById(R.id.postCodeField).setVisibility(View.VISIBLE);
                    findViewById(R.id.countryField).setVisibility(View.VISIBLE);
                    findViewById(R.id.pcInfo).setVisibility(View.VISIBLE);

                    // Get the text from the EditText views for the user-input address
                    String userAddress = ((EditText) findViewById(R.id.addressField)).getText().toString();
                    String userCity = ((EditText) findViewById(R.id.cityField)).getText().toString();
                    String userPostCode = ((EditText) findViewById(R.id.postCodeField)).getText().toString();
                    String userCountry = ((EditText) findViewById(R.id.countryField)).getText().toString();

                    // Combine the user-input address details
                    new_addressDelivery = userAddress + ",\n" + userPostCode + ",\n" + userCity + ",\n" + userCountry;
                } else {
                    // Hide relevant EditText views for address
                    findViewById(R.id.addressField).setVisibility(View.GONE);
                    findViewById(R.id.cityField).setVisibility(View.GONE);
                    findViewById(R.id.postCodeField).setVisibility(View.GONE);
                    findViewById(R.id.countryField).setVisibility(View.GONE);
                    findViewById(R.id.pcInfo).setVisibility(View.GONE);
                    new_addressDelivery = addressDelivery;

                    // Use the existing address details (retrieved from Firebase)

                }
            }
        });

        // Retrieve user details from Firebase
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userReference = FirebaseDatabase.getInstance("https://assessment2-4390a-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Registered Users").child(userId);

            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ReadWriteUserDetails userDetails = dataSnapshot.getValue(ReadWriteUserDetails.class);

                    if (userDetails != null) {
                        // Display order total (you may replace this with your actual order total logic)
                        orderTotalTextView.setText("£"+ Float.toString(total));
                        orderTotalText2.setText("Order Total: £"+Float.toString(total));


                        // Display payment method
                        paymentMethod = String.format(
                                "Card ending in %s,\n Exp: %s",
                                userDetails.acc_num.substring(userDetails.acc_num.length() - 4),
                                userDetails.acc_exp_date
                        );
                        paymentMethodTextView.setText(paymentMethod);

                        addressDelivery = userDetails.address + ",\n" +
                                userDetails.postcode + ",\n" +
                                userDetails.city + ",\n" +
                                userDetails.country;
                        address.setText(addressDelivery);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors in data retrieval
                }
            });
        }



        deliveryRadioButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                if (checkedId == R.id.expressDelivery){
                    total +=5.00f;
                    orderTotalTextView.setText("£"+Float.toString(total));
                    orderTotalTextView.requestFocus();
                    orderTotalText2.setText("Order Total: £"+Float.toString(total));


                }
                else{
                    orderTotalTextView.setText("£"+ Float.toString(total));
                    orderTotalText2.setText("Order Total: £"+Float.toString(total));
                }
                deliveryOption = selectedRadioButton.getText().toString();

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderConfirmed();

            }
        });




    }



    private void toastMessage(int x){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, null);
        Toast toast = new Toast(getApplicationContext());
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

    private void cancel(){
        Intent intent = new Intent(CheckOut.this, BasketFragment.class);
        startActivity(intent);
    }

    private void orderConfirmed(){

        Intent intent = new Intent(CheckOut.this, OrderPlacedActivity.class);
        intent.putExtra("deliveryOption", deliveryOption);
        intent.putExtra("deliveryAddress",new_addressDelivery);
        intent.putExtra("payment",new_payment_method);
        intent.putExtra("total_value",String.valueOf(total));

        startActivity(intent);

    }
}