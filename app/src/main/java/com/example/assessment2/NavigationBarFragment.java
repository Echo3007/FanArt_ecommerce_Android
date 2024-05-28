/*Student Name: Angela Pellillo
 * Student ID: 21499500
 * Module: Mobile Web Application Development
 * Module ID: CP5CS93E*/

package com.example.assessment2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class NavigationBarFragment extends Fragment {

   private ImageButton fragmentHome, fragmentBasket, fragmentUser, fragmentSetting;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation_bar, container, false);

        fragmentHome = view.findViewById(R.id.fragmentHome);

        fragmentHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackHome();
            }
        });

        fragmentBasket = view.findViewById(R.id.fragmentBasket);
        fragmentBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBasket();
            }
        });

        fragmentUser = view.findViewById(R.id.fragmentUser);
        fragmentUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfile();
            }
        });

        fragmentSetting = view.findViewById(R.id.fragmentSettings);
        fragmentSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettings();
            }
        });


        return view;


    }


    private void goBackHome(){
        FragmentHome homeFragment = new FragmentHome();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentHome, homeFragment)
                .addToBackStack(null)
                .commit();
    }

    private void showBasket(){
        BasketFragment basketFragment = new BasketFragment();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentHome, basketFragment)
                .addToBackStack(null)
                .commit();

    }

    private void showProfile(){
        UserProfileFragment userFragment = new UserProfileFragment();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentHome, userFragment)
                .addToBackStack(null)
                .commit();
    }

    private void showSettings(){
        SettingFragment settingFragment = new SettingFragment();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentHome, settingFragment)
                .addToBackStack(null)
                .commit();
    }

}
