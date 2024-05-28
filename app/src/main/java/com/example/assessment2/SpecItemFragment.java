/*Student Name: Angela Pellillo
 * Student ID: 21499500
 * Module: Mobile Web Application Development
 * Module ID: CP5CS93E*/

package com.example.assessment2;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class SpecItemFragment extends Fragment {

    private DatabaseReference reference = null;
    private NumberPicker quantityPicker;
    private Button addBasket, goCheckOut;
    private TextView name, itemDescr, artistName, price, stockText,quantityText;
    private ImageView image;
    private String productId,imageUrl;
    int quantity_selected;
    int max_stock;
    float total;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spec_item, container, false);


        stockText = view.findViewById(R.id.stockText);
        quantityText = view.findViewById(R.id.quantityText);

        quantityPicker = view.findViewById(R.id.quantityPicker);
        quantityPicker.setMinValue(0);
        quantityPicker.setWrapSelectorWheel(false);
        quantityPicker.setBackgroundColor(Color.WHITE);
        quantityPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            quantity_selected = newVal;
        });



        // Retrieve the ID from arguments
        Bundle args = getArguments();
        if (args != null) {
            productId = args.getString("productId", "");
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://assessment2-4390a-default-rtdb.europe-west1.firebasedatabase.app/");
            if (database != null) {
                reference = database.getReference("Products");
                ValueEventListener firebaseListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Products product = snapshot.getValue(Products.class);
                            if (product != null && Integer.parseInt(productId) == product.getId()) {
                                image = view.findViewById(R.id.imageSpecItem);
                                name = view.findViewById(R.id.nameSpecItem);
                                itemDescr = view.findViewById(R.id.itemDescr);
                                artistName = view.findViewById(R.id.artistName);
                                price = view.findViewById(R.id.priceText);
                                name.setText(product.getName());
                                itemDescr.setText(product.getDescr());
                                Picasso.get().load(product.getImage()).into(image);
                                artistName.setText(product.getArtistName());
                                price.setText("£"+product.getPrice());
                                max_stock = product.getQuantity();
                                imageUrl = product.getImage();
                                if(max_stock ==0){
                                    stockText.setText("Out of Stock");
                                    stockText.setTextColor(Color.RED);
                                    quantityPicker.setVisibility(View.GONE);
                                    quantityText.setVisibility(View.GONE);
                                    addBasket.setVisibility(View.GONE);

                                }


                                quantityPicker.setMaxValue(product.getQuantity());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle errors in data retrieval
                    }
                };

                // Attach ValueEventListener to the database reference
                reference.addValueEventListener(firebaseListener);
            }

        }

        addBasket = view.findViewById(R.id.addBasketBtn);

        addBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAddBasket(productId, quantity_selected);
            }
        });

        return view;
    }

    private void setAddBasket(String productId, int quantity_selected) {
        DatabaseReference basketReference = FirebaseDatabase.getInstance("https://assessment2-4390a-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Basket");

        ValueEventListener basketCheckListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // The "Basket" node doesn't exist, create it
                    basketReference.setValue("Basket created");
                }

                // Proceed to add the item to the basket
                addBasketItemToFirebase(basketReference, Integer.parseInt(productId), quantity_selected);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors in data retrieval
            }
        };

        // Attach ValueEventListener to check if the "Basket" node exists
        basketReference.addListenerForSingleValueEvent(basketCheckListener);
    }

    private void addBasketItemToFirebase(DatabaseReference basketReference, int productId, int quantity_selected) {
        ValueEventListener basketItemListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean itemExists = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BasketItem existingItem = snapshot.getValue(BasketItem.class);
                    if (existingItem != null && existingItem.getId() == productId) {
                        itemExists = true;
                        int updatedQuantity = existingItem.getQuantity() + quantity_selected;
                        if (updatedQuantity <= max_stock) {
                            float updatedTotal = updatedQuantity * existingItem.getPrice();
                            snapshot.getRef().child("quantity").setValue(updatedQuantity);
                            snapshot.getRef().child("total").setValue(updatedTotal);

                            // Update the existing BasketFragment with the new item
                            updateBasketFragment(Integer.toString(productId), updatedQuantity);
                        } else {
                            Toast.makeText(getContext(), "Sorry, product out of stock.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }

                if (!itemExists) {
                    // If the item is not in the basket, add a new entry
                    BasketItem basketItem = new BasketItem();
                    basketItem.setId(productId);
                    basketItem.setName(name.getText().toString());
                    basketItem.setArtistName(artistName.getText().toString());
                    basketItem.setPrice(Float.parseFloat(price.getText().toString().substring(1))); // Assuming price is formatted as £X.XX
                    basketItem.setQuantity(quantity_selected);
                    basketItem.setImage(imageUrl.toString());

                    float total = quantity_selected * basketItem.getPrice();
                    basketItem.setTotal(total);

                    basketReference.push().setValue(basketItem);

                    // Update the existing BasketFragment with the new item
                    updateBasketFragment(Integer.toString(productId), quantity_selected);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors in data retrieval
            }
        };

        basketReference.addListenerForSingleValueEvent(basketItemListener);
    }

    private void updateBasketFragment(String productId, int quantity) {
        BasketFragment basketFragment = new BasketFragment();
        Bundle args = new Bundle();
        args.putString("productId", String.valueOf(productId));
        args.putInt("quantity", quantity);
        basketFragment.setArguments(args);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentHome, basketFragment)
                .addToBackStack(null)
                .commit();
    }
}