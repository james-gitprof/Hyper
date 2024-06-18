package com.example.carcab.UserPageFeature.Customers.ViewModels;

import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository.FirebaseConnector;

public class UserViewModelHandler {

    private static UserViewModelHandler mUserViewModelHandler;
    private static UserViewModel mUserViewModel;

    private UserViewModelHandler()
    {
        mUserViewModel = UserViewModel.getInstance();
    }

    public static UserViewModelHandler getInstance()
    {
        if (mUserViewModelHandler == null)
        {
            mUserViewModelHandler = new UserViewModelHandler();
        }
        return mUserViewModelHandler;
    }

    public void fetchLoggedInUserData()
    {
        mUserViewModel.updateUserInSession(FirebaseConnector.getInstance().getFirebaseAuthInstance().getCurrentUser());

    }


}
