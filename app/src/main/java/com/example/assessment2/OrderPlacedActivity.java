package com.example.assessment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class OrderPlacedActivity extends AppCompatActivity {

    private TextView delivery,orderValue,paymentDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);

        delivery = findViewById(R.id.delivery);
        paymentDetails = findViewById(R.id.paymentDetails);
        orderValue = findViewById(R.id.orderValue);



       /* intent.putExtra("deliveryOption", deliveryOption);
        intent.putExtra("deliveryAddress",new_addressDelivery);
        intent.putExtra("payment",new_payment_method);
        intent.putExtra("total_value",total);*/

        orderValue.setText("£"+getIntent().getExtras().getString("total_value"));
        paymentDetails.setText(getIntent().getExtras().getString("payment"));
        delivery.setText(getIntent().getExtras().getString("deliveryAddress")+"\n"+"\n"+getIntent().getExtras().getString("deliveryOption"));



    }

}