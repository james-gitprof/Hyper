package com.example.carcab.AuthenticateFeature.AuthenticationComponents.ViewModels;

import androidx.lifecycle.ViewModel;

import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository.FirebaseConnector;

public class AuthenticationViewModel extends ViewModel {
    /*
    WARNING: NOT THREAD-SAFE!!
    Modify this singleton class when working with threads if its needed
    Else ur gonna have a hard time lol
     */
    private static AuthenticationViewModel mSelfAuthVM;
    private static FirebaseConnector mFirebase;
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
