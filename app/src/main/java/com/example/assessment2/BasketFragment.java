/*Student Name: Angela Pellillo
 * Student ID: 21499500
 * Module: Mobile Web Application Development
 * Module ID: CP5CS93E*/


package com.example.assessment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BasketFragment extends Fragment {

    private List<BasketItem> basketItems = new ArrayList<>();
    private BasketAdapter basketAdapter;
    private Button contBtn, checkOutBtn;
    private RecyclerView recyclerView;
    private TextView basketSubTotal;
    private float totalAmount = 0.0f;
    private String productId;
    private int quantity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        contBtn = view.findViewById(R.id.contBtn);
        checkOutBtn = view.findViewById(R.id.checkOutBtn);
        recyclerView = view.findViewById(R.id.basketRecyclerView);
        basketSubTotal = view.findViewById(R.id.basketSubTotal);



        Bundle args = getArguments();
        if (args != null) {
            productId = args.getString("productId", "");
            quantity = args.getInt("quantity", 0);

        }

        contBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contShopping();
            }
        });

        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCheckOut();
            }
        });

        // Initialize RecyclerView and Adapter
        basketAdapter = new BasketAdapter(basketItems);
        recyclerView.setAdapter(basketAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        basketAdapter.setOnItemClickListener(new BasketAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String productId) {
                // Handle item click, e.g., navigate to SpecItem Fragment
                navigateToSpecItemFragment(productId);
            }
        });
        // Set the OnDeleteItemClickListener
        basketAdapter.setOnDeleteItemClickListener(new BasketAdapter.OnDeleteItemClickListener() {
            @Override
            public void onDeleteItemClick(int position) {
                // Handle delete item logic here
                deleteItemFromBasket(position);
            }
        });


        // Retrieve data from Firebase
        DatabaseReference basketReference = FirebaseDatabase.getInstance("https://assessment2-4390a-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Basket");
        basketReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                basketItems.clear(); // Clear existing items before adding new ones
                totalAmount = 0.0f; // Reset totalAmount before calculating again

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BasketItem basketItem = snapshot.getValue(BasketItem.class);
                    if (basketItem != null) {
                        basketItems.add(basketItem);
                        totalAmount += basketItem.getTotal(); // Add the total of each item to the totalAmount
                    }
                }

                // Update the TextView with the calculated total
                basketSubTotal.setText("Subtotal: £" + String.format("%.2f", totalAmount));

                basketAdapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors in data retrieval
            }
        });

        return view;
    }

    private void contShopping() {
        FragmentHome fragmentHome  = new FragmentHome();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentHome, fragmentHome)
                .addToBackStack(null)
                .commit();
    }

    private void goCheckOut(){

        CheckOutFragment checkOutFragment  = new CheckOutFragment();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentHome, checkOutFragment)
                .addToBackStack(null)
                .commit();

        //Args passed to checkout fragment to then update database
        Bundle args = new Bundle();
        args.putFloat("totalAmount", totalAmount);
        args.putString("productId",productId);
        args.putInt("quantity",quantity);
        checkOutFragment.setArguments(args);

    }

    private void deleteItemFromBasket(int position) {
        // Remove the item from your dataset
        BasketItem deletedItem = basketItems.get(position);
        float deletedItemTotal = deletedItem.getTotal();
        basketItems.remove(position);

        // Update the total amount
        totalAmount -= deletedItemTotal;

        // Update the UI with the new total amount
        basketSubTotal.setText("Subtotal: £" + String.format("%.2f", totalAmount));

        // Notify the adapter about the item removal
        basketAdapter.notifyItemRemoved(position);

        // Delete the item from Firebase
        DatabaseReference basketReference = FirebaseDatabase.getInstance("https://assessment2-4390a-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Basket");

        basketReference.orderByChild("id").equalTo(deletedItem.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Successfully removed from Firebase
                                } else {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors in data retrieval
            }
        });
    }

    public void onItemClick(String productId) {
        // Handle item click, e.g., navigate to SpecItem Fragment
        navigateToSpecItemFragment(productId);
    }

    private void navigateToSpecItemFragment(String productId) {
        // Create a new instance of the SpecItemFragment
        SpecItemFragment specItemFragment = new SpecItemFragment();

        // Pass the productId to the SpecItemFragment using a Bundle
        Bundle args = new Bundle();
        args.putString("productId", productId);
        specItemFragment.setArguments(args);

        // Replace the current fragment with SpecItemFragment
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentHome, specItemFragment)
                .addToBackStack(null)
                .commit();
    }

}
