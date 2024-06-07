package com.example.carcab.AuthenticationViews.Abstractions;

import android.util.Log;

import com.example.carcab.CompAuthentication.ViewModels.AuthenticationViewModel;

public abstract class AuthFragment extends SwitchableFragment {

    protected static AuthenticationViewModel mAuthViewModel;

    public AuthFragment()
    {
        Log.d("AUTH_FRAGMENT", "View Model initialized;");
        mAuthViewModel = AuthenticationViewModel.getInstance();
    }

    public AuthenticationViewModel getResponsibleViewModel()
    {
        return this.mAuthViewModel;
    }

}
