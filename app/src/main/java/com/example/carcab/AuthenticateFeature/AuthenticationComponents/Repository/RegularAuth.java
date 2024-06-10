package com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository;

import androidx.annotation.NonNull;

import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Models.UserInfo;
import com.example.carcab.AuthenticateFeature.AuthenticationComponents.ViewModels.AuthViewModelHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.HashMap;
import java.util.Map;

public class RegularAuth implements IAuthenticate {

    @Override
    public boolean performRegister(UserInfo info) {
        boolean registerResult = FirebaseConnector.getInstance()
                .getFirebaseAuthInstance()
                .createUserWithEmailAndPassword(info.getEmail(), info.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            if (info.isDriver())
                            {
                            /*
                            If user is a driver, create a record on the database.
                            Parent: Users -> Drivers
                            UID :
                            {
                                email: String
                                vehicleType: String
                            }
                             */
                                Map<java.lang.String, java.lang.String> details = new HashMap<>();
                                try {
                                    details.put("email", info.getEmail());
                                    details.put("vehicleType", info.getVehicle().getVehicleType());
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                FirebaseConnector.getInstance()
                                        .getFirebaseDatabaseInstance()
                                        .child("Users")
                                        .child("Drivers")
                                        .child(FirebaseConnector
                                                .getInstance()
                                                .getFirebaseAuthInstance()
                                                .getCurrentUser()
                                                .getUid())
                                        .setValue(details);

                            }
                            else
                            {
                            /*
                            If user is a regular user
                            Parent: Users -> Customers
                            UID : {
                                email: String
                            }
                             */
                                Map<java.lang.String, java.lang.String> details = new HashMap<>();
                                details.put("email", info.getEmail());
                                FirebaseConnector.getInstance()
                                        .getFirebaseDatabaseInstance()
                                        .child("Users")
                                        .child("Customers")
                                        .child(FirebaseConnector
                                                .getInstance()
                                                .getFirebaseAuthInstance()
                                                .getUid())
                                        .setValue(details);
                            }
                            // Afterwards, clear the cache in case.
                            // By default, on registration success Firebase will set the current user
                            // to the new one. We don't want that thing yet
                            // We will prompt them to login again.
                            performSignOut();
                            // Invoke the observer to update UI
                            AuthViewModelHandler.getInstance().raiseRegistrationSuccess(FirebaseConnector.getInstance()
                                    .getFirebaseAuthInstance()
                                    .getCurrentUser());
                        }
                        else
                        {
                            AuthViewModelHandler.getInstance().raiseAuthenticationException(task.getException());
                            // delete the user because it creates a new entry despite failure
                            // might look into it once polishing starts but for now, this is just a
                            // band-aid fix.

                            // implementation coming soon

                        }
                    }
                })
                .isSuccessful();
        return registerResult;
    }

    @Override
    public boolean performLogin(UserInfo info) {
        boolean loginResult = FirebaseConnector.getInstance()
                .getFirebaseAuthInstance()
                .signInWithEmailAndPassword(info.getEmail(), info.getPassword())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        AuthViewModelHandler.getInstance()
                                .raiseAuthenticationException(e);
                    }
                })
                .isSuccessful();
        return loginResult;
    }

    @Override
    public void performSignOut() {
        FirebaseConnector.getInstance()
                .getFirebaseAuthInstance()
                .signOut();
    }
}
