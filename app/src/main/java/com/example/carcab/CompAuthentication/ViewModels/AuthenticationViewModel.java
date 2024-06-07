package com.example.carcab.CompAuthentication.ViewModels;

import com.example.carcab.CompAuthentication.Repository.FirebaseConnector;

public class AuthenticationViewModel {
    /*
    WARNING: NOT THREAD-SAFE!!
    Modify this singleton class when working with threads if its needed
    Else ur gonna have a hard time lol
     */
    private static AuthenticationViewModel mSelfAuthVM;
    private static FirebaseConnector mFirebase;

    // prevent others from creating object of this class
    private AuthenticationViewModel()
    {
        // When initialized, provide connection to firebase repository through connector
        mFirebase = FirebaseConnector.getInstance();

    }

    public static AuthenticationViewModel getInstance()
    {
        if (mSelfAuthVM == null)
        {
            mSelfAuthVM = new AuthenticationViewModel();
        }
        return mSelfAuthVM;
    }
}
