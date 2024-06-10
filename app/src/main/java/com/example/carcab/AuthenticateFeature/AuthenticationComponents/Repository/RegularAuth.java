package com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Models.UserInfo;
import com.example.carcab.AuthenticateFeature.AuthenticationComponents.ViewModels.AuthStrictViewModel;
import com.example.carcab.AuthenticateFeature.AuthenticationComponents.ViewModels.AuthViewModelHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

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
    public void performLogin(UserInfo info) {
         FirebaseConnector.getInstance()
                .getFirebaseAuthInstance()
                .signInWithEmailAndPassword(info.getEmail(), info.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful() == false)
                        {
                            AuthViewModelHandler.getInstance().raiseAuthenticationException(task.getException());
                        }
                        else
                        {
                            Log.d("AUTH-LOGIN-PERFORMER", "Authentication success!");
                            FirebaseConnector.getInstance().getFirebaseAuthInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                                @Override
                                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                    FirebaseUser loggedUser = firebaseAuth
                                            .getCurrentUser();
                                    if (loggedUser != null)
                                    {
                                        String userUID = loggedUser.getUid();
                                        // check which user group this one belongs to...
                                        DatabaseReference driverGroupRef = FirebaseConnector.getInstance()
                                                .getFirebaseDatabaseInstance()
                                                .child("Users")
                                                .child("Drivers");
                                        UserInfo userMetadataModified = info;
                                        driverGroupRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists())
                                                {
                                                    for (DataSnapshot subSnapShot : snapshot.getChildren())
                                                    {
                                                        if (subSnapShot.getKey().equals(userUID))
                                                        {
                                                            Log.d("AUTH-DRIVER-FINDER-STATUS", "Found a driver based on entered credentials!");

                                                            userMetadataModified.setDriver(true);
                                                            break;
                                                        }
                                                    }

                                                    AuthStrictViewModel.getInstance().updateLocalUserSessionState(userMetadataModified);
                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                // idk what to do with this one yet...
                                            }
                                        });

                                    }
                                    else
                                    {
                                        AuthViewModelHandler.getInstance().raiseMyErrorState(new Exception("Something went wrong authenticating the user to the server."));
                                    }
                                }
                            });
                        }
                    }
                });
    }

    @Override
    public void performSignOut() {
        FirebaseConnector.getInstance()
                .getFirebaseAuthInstance()
                .signOut();
    }
}
