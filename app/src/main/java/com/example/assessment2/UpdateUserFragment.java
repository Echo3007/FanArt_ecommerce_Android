/*Student Name: Angela Pellillo
 * Student ID: 21499500
 * Module: Mobile Web Application Development
 * Module ID: CP5CS93E*/

package com.example.assessment2;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UpdateUserFragment extends Fragment {

    private TextView userName, show_email;
    private EditText show_psw, show_new_psw, show_fullname, show_DOB, show_gender, show_AddressLine, show_city, show_postcode,
            show_country, show_cardName, show_cardNum, show_cardExp, show_cardCVV;
    private String  text_email, text_fullname, text_dob, text_gender, text_AddressLine, text_city, text_postcode,
            text_country, text_cardName, text_cardNum, text_cardExp, text_cardCVV, profilePic;
    private ImageView profilePicture;
    private FirebaseAuth authProfile;
    private FirebaseUser firebaseuser;
    private Button updateBtn, cancelBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_user, container, false);

        userName = view.findViewById(R.id.userName);
        show_email = view.findViewById(R.id.show_email);
        show_psw = view.findViewById(R.id.show_psw);
        show_new_psw = view.findViewById(R.id.show_new_psw);
        show_fullname = view.findViewById(R.id.show_fullname);
        show_DOB = view.findViewById(R.id.show_DOB);
        show_gender = view.findViewById(R.id.show_gender);
        show_AddressLine = view.findViewById(R.id.show_AddressLine);
        show_city = view.findViewById(R.id.show_city);
        show_postcode = view.findViewById(R.id.show_postcode);
        show_country = view.findViewById(R.id.show_country);
        show_cardName = view.findViewById(R.id.show_cardName);
        show_cardNum = view.findViewById(R.id.show_cardNum);
        show_cardExp = view.findViewById(R.id.show_cardExp);
        show_cardCVV = view.findViewById(R.id.show_cardCVV);

        profilePicture = view.findViewById(R.id.profilePic);
        authProfile = FirebaseAuth.getInstance();
        firebaseuser = authProfile.getCurrentUser();

        if(firebaseuser == null){
            Toast.makeText(getContext(), "Something went wrong. User details are currently unavailable.", Toast.LENGTH_SHORT).show();

        } else {
            showUserDets(firebaseuser);

        }

        updateBtn = view.findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatePasswords()) {
                    updateUserDetails();
                    goBackProfile();
                } else {
                    Toast.makeText(getContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelBtn = view.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackProfile();
            }
        });

        return view;
    }

    //To check if the passwords inserted match
    private boolean validatePasswords() {
        String newPassword = show_psw.getText().toString();
        String confirmPassword = show_new_psw.getText().toString();
        return newPassword.equals(confirmPassword);
    }

    private void updateUserDetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Registered Users");
        String userID = firebaseuser.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails userDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (userDetails != null) {
                    // Modify the user details based on the changes made by the user
                    userDetails.fullname = show_fullname.getText().toString();
                    userDetails.dob = show_DOB.getText().toString();
                    userDetails.gender = show_gender.getText().toString();
                    userDetails.address = show_AddressLine.getText().toString();
                    userDetails.city = show_city.getText().toString();
                    userDetails.postcode = show_postcode.getText().toString();
                    userDetails.country = show_country.getText().toString();
                    userDetails.acc_name = show_cardName.getText().toString();
                    userDetails.acc_num = show_cardNum.getText().toString();
                    userDetails.acc_exp_date = show_cardExp.getText().toString();
                    userDetails.acc_cvv_num = show_cardCVV.getText().toString();

                    // Update details in Firebase
                    updateDetailsInFirebase(userID, userDetails);

                    // Check if a new password is provided
                    String newPassword = show_new_psw.getText().toString();
                    if (!newPassword.isEmpty()) {
                        // Update password
                        updatePassword(newPassword);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });
    }

    private void updateDetailsInFirebase(String userID, ReadWriteUserDetails userDetails) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Registered Users");
        reference.child(userID).setValue(userDetails)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "User details updated successfully.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Failed to update user details.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updatePassword(String newPassword) {
        firebaseuser.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                           Toast.makeText(getContext(), "Password updated successfully.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Failed to update password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
                if (readUserDetails != null) {
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
                    text_cardExp = readUserDetails.acc_exp_date;
                    text_cardCVV = readUserDetails.acc_cvv_num;

                    show_email.setText(text_email);
                    show_fullname.setText(text_fullname);
                    show_DOB.setText(text_dob);
                    show_AddressLine.setText(text_AddressLine);
                    show_city.setText(text_city);
                    show_country.setText(text_country);
                    show_postcode.setText(text_postcode);
                    show_gender.setText(text_gender);
                    show_cardName.setText(text_cardName);
                    show_psw.setHint("Enter new password");
                    show_cardNum.setText(text_cardNum);
                    show_cardExp.setText(text_cardExp);
                    show_cardCVV.setText(text_cardCVV);

                    // Load profile picture using Picasso

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

    private void goBackProfile(){
        UserProfileFragment userProfile = new UserProfileFragment();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentHome, userProfile)
                .addToBackStack(null)
                .commit();

    }
}
