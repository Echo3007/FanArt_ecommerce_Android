/*Student Name: Angela Pellillo
 * Student ID: 21499500
 * Module: Mobile Web Application Development
 * Module ID: CP5CS93E*/

package com.example.assessment2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FanArtFragment extends Fragment implements HomeRecycler {
    private ArrayList<Products> products_models = new ArrayList<>();
    private FirebaseDatabase database = null;
    private RecyclerView recyclerView;
    private DatabaseReference reference = null;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fan_art, container, false);

        database = FirebaseDatabase.getInstance("https://assessment2-4390a-default-rtdb.europe-west1.firebasedatabase.app/");
        if (database != null) {
            reference = database.getReference("Products");
            ValueEventListener firebaseListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Handle data changes from Firebase
                    products_models.clear(); // Clear the list before populating it with new data
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Products product = snapshot.getValue(Products.class);
                        if (product != null && product.getCategory() != null && product.getCategory().toLowerCase().contains("fanart")) {
                            // Add only the products with "FanArt" in their name
                            products_models.add(product);
                        }
                    }
                    recyclerViewAdapter.notifyDataSetChanged();
                    Log.d("FragmentHome", "onDataChange: " + dataSnapshot.getChildrenCount());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors in data retrieval
                }
            };

            // Attach ValueEventListener to the database reference
            reference.addValueEventListener(firebaseListener);
        }

        // Find the RecyclerView within the fragment's layout
        recyclerView = view.findViewById(R.id.FanArtRecyclerView);

        // Set up the RecyclerView
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), products_models, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Products selectedProduct = products_models.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("productId", String.valueOf(selectedProduct.getId()));

        SpecItemFragment specItemFragment = new SpecItemFragment();
        specItemFragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentHome, specItemFragment)
                .addToBackStack(null)
                .commit();
    }
}
