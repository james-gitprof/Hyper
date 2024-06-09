package com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DatabaseDriverFinder implements IDataFinder<String> {
    @Override
    public boolean isAvailable(String UID) {
        final boolean[] isDriver = {false};
        DatabaseReference driverGroupRef = FirebaseConnector.getInstance()
                .getFirebaseDatabaseInstance()
                .child("Users")
                .child("Drivers");
        driverGroupRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot subSnapShot : snapshot.getChildren())
                    {
                        if (subSnapShot.getKey().equals(UID))
                        {
                            isDriver[0] = true;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // idk what to do with this one yet...
            }
        });
        return isDriver[0];
    }
}
