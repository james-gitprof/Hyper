package com.example.carcab.UserPageFeature.Customers.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

public class UserViewModel extends ViewModel
{
    private static UserViewModel mUserViewModel;

    private UserViewModel()
    {

    }

    public void updateUserInSession(FirebaseUser user)
    {
        this.userInSession.setValue(user);
    }

    public static UserViewModel getInstance()
    {
        if (mUserViewModel == null)
        {
            mUserViewModel = new UserViewModel();
        }
        return mUserViewModel;
    }

    private final MutableLiveData<FirebaseUser> userInSession = new MutableLiveData<>();
    public LiveData<FirebaseUser> getUserSessionState(){
        return this.userInSession;
    }

}
