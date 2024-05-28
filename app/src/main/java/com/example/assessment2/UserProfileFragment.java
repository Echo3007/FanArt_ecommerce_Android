/*Student Name: Angela Pellillo
 * Student ID: 21499500
 * Module: Mobile Web Application Development
 * Module ID: CP5CS93E*/


package com.example.assessment2;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class UserProfileFragment extends Fragment {
    private TextView userName, show_email,show_psw,show_fullname,show_DOB,show_gender,show_AddressLine,show_city,show_postcode,
            show_country,show_cardName, show_cardNum;
    private String text_email, text_fullname, text_dob, text_gender,profilePic,
            text_AddressLine, text_city, text_postcode, text_country, text_cardName, text_cardNum;
    private ImageView profilePicture;
    private FirebaseAuth authProfile;
    private FirebaseUser firebaseuser;
    private Button edit;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // Bind TextViews
        userName = view.findViewById(R.id.userName);
        show_email = view.findViewById(R.id.show_email);
        show_psw = view.findViewById(R.id.show_psw);
        show_fullname = view.findViewById(R.id.show_fullname);
        show_DOB = view.findViewById(R.id.show_DOB);
        show_gender = view.findViewById(R.id.show_gender);
        show_AddressLine = view.findViewById(R.id.show_AddressLine);
        show_city = view.findViewById(R.id.show_city);
        show_postcode = view.findViewById(R.id.show_postcode);
        show_country = view.findViewById(R.id.show_country);
        show_cardName = view.findViewById(R.id.show_cardName);
        show_cardNum = view.findViewById(R.id.show_cardNum);
        edit = view.findViewById(R.id.editDetails);

        profilePicture = view.findViewById(R.id.profilePic);
        authProfile = FirebaseAuth.getInstance();
        firebaseuser = authProfile.getCurrentUser();

        if(firebaseuser == null){
            Toast.makeText(getContext(), "Something went wrong. User details are currently unavailable.", Toast.LENGTH_SHORT).show();

        }else{
            showUserDets(firebaseuser);

        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDetails();
            }
        });

        return view;
    }

    private void showUserDets(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();

        //Retrieving data from database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Registered Users");
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               User user = snapshot.getValue(User.class);
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if(readUserDetails !=null){
                    text_email = firebaseUser.getEmail();
                    text_fullname = readUserDetails.fullname;
                    text_dob = readUserDetails.dob;
                    text_gender = readUserDetails.gender;

                    text_AddressLine = readUserDetails.address;
                    text_city = readUserDetails.city;
                    text_postcode = readUserDetails.postcode;
                    text_country = readUserDetails.country;
                    text_cardName = readUserDetails.acc_name;
                    text_cardNum = readUserDetails.acc_num;

                    show_email.setText(text_email);
                    show_fullname.setText(text_fullname);
                    show_DOB.setText(text_dob);
                    show_AddressLine.setText(text_AddressLine);
                    show_city.setText(text_city);
                    show_country.setText(text_country);
                    show_postcode.setText(text_postcode);
                    show_gender.setText(text_gender);
                    show_cardName.setText(text_cardName);
                    show_psw.setText("1111111111");
                    show_cardNum.setText(text_cardNum);


                    profilePic = readUserDetails.profilePicture;
                    Picasso.get().load(profilePic).into(profilePicture);

                    userName.setText(user.getUsername().toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void editDetails(){
        UpdateUserFragment updateDetails = new UpdateUserFragment();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentHome, updateDetails)
                .addToBackStack(null)
                .commit();

    }


}