package com.example.carcab.CompAuthentication.Repository;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseConnector {

    /*
    WARNING: NOT THREAD-SAFE!!
     */
    private static FirebaseConnector mSelfFirebase;
    private FirebaseAuth mAuth;

    private FirebaseConnector()
    {
        // dont allow others to instantiate multiple instances of this thing
    }

    public static FirebaseConnector getInstance()
    {
        if (mSelfFirebase == null)
        {
            mSelfFirebase = new FirebaseConnector();
        }
        return mSelfFirebase;
    }

    public FirebaseAuth getFirebaseAuthInstance()
    {
        return this.mAuth;
    }






}
