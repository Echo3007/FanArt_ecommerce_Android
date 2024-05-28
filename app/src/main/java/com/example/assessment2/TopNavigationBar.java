/*Student Name: Angela Pellillo
 * Student ID: 21499500
 * Module: Mobile Web Application Development
 * Module ID: CP5CS93E*/

package com.example.assessment2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TopNavigationBar extends Fragment {

  private TextView  fanArt, fanFic;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_navigation_bar, container, false);



        fanArt = view.findViewById(R.id.fanArtTextView);
        fanArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFanArtFragment();
            }
        });

        fanFic = view.findViewById(R.id.fanFicView);
        fanFic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFanFicFragment();
            }
        });

        return view;

    }

    private void openFanArtFragment(){
        FanArtFragment fanArtFragment = new FanArtFragment();
        // Use a FragmentTransaction to replace the current fragment with the ShopFragment
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentHome, fanArtFragment)
                .addToBackStack(null)
                .commit();

    }

    private void openFanFicFragment(){
        FanFicFragment fanFicFragment = new FanFicFragment();
        // Replace the current fragment with the ShopFragment using Fragment Transaction
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentHome, fanFicFragment)
                .addToBackStack(null)
                .commit();

    }

}