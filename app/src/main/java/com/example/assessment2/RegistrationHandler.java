package com.example.assessment2;

import android.telephony.ims.RegistrationManager;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class RegistrationHandler {
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    public void registerUser(String email, String password, RegistrationCallback callback) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        Log.e("RegistrationHandler", "User registration failed", task.getException());
                        callback.onFailure(task.getException());
                    }
                });
    }
}
