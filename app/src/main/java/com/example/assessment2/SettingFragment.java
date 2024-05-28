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

import com.google.firebase.auth.FirebaseAuth;


public class SettingFragment extends Fragment {

    TextView faqTitle, logout, q1, q2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        faqTitle = view.findViewById(R.id.faqTitle);
        logout = view.findViewById(R.id.logOut);
        q1 = view.findViewById(R.id.q1);
        q2 = view.findViewById(R.id.q2);

        q1.setVisibility(View.GONE);
        q2.setVisibility(View.GONE);

        faqTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (q1.getVisibility() == View.VISIBLE) {
                    q1.setVisibility(View.GONE);
                    q2.setVisibility(View.GONE);
                } else {
                    q1.setVisibility(View.VISIBLE);
                    q2.setVisibility(View.VISIBLE);
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log out the user using Firebase Authentication
                FirebaseAuth.getInstance().signOut();

                // Navigate back to the main activity
                startActivity(new Intent(getActivity(), MainActivity.class));

                // Close the current activity
                getActivity().finish();
            }
        });


        return view;
    }
}