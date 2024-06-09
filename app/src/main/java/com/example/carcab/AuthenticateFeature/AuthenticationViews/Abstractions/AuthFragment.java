package com.example.carcab.AuthenticateFeature.AuthenticationViews.Abstractions;

import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.example.carcab.AuthenticateFeature.AuthenticationComponents.ViewModels.AuthenticationViewModel;

public abstract class AuthFragment extends SwitchableFragment {

    protected static AuthenticationViewModel mAuthViewModel;

    public AuthFragment()
    {

    }


}
