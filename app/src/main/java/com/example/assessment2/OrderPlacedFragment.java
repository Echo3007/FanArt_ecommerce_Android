/*Student Name: Angela Pellillo
 * Student ID: 21499500
 * Module: Mobile Web Application Development
 * Module ID: CP5CS93E*/

package com.example.assessment2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class OrderPlacedFragment extends Fragment {
    private TextView delivery,orderValue,paymentDetails;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_placed, container, false);

        delivery = view.findViewById(R.id.delivery);
        paymentDetails = view.findViewById(R.id.paymentDetails);
        orderValue = view.findViewById(R.id.orderValue);


       Bundle args = getArguments();
        if (args != null) {
            orderValue.setText("£"+args.getString("total_value"));
            paymentDetails.setText(args.getString("payment"));
            delivery.setText(args.getString("deliveryAddress")+"\n"+"\n"+args.getString("deliveryOption"));




            Log.d("OrderPlacedFragment", "Delivery Option: " + args.getString("deliveryOption"));
            Log.d("OrderPlacedFragment", "Delivery Address: " + args.getString("deliveryAddress"));
            Log.d("OrderPlacedFragment", "Payment: " + args.getString("payment"));
            Log.d("OrderPlacedFragment", "Total Value: " + args.getString("total_value"));

        }


        return view;
    }
}