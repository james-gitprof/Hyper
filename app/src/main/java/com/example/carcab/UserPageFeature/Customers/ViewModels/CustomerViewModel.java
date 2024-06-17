package com.example.carcab.UserPageFeature.Customers.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

public class CustomerViewModel extends ViewModel
{
    private static CustomerViewModel mCustomerViewModel;

    private CustomerViewModel()
    {

    }

    public void updateUserInSession(FirebaseUser user)
    {
        this.userInSession.setValue(user);
    }

    public static CustomerViewModel getInstance()
    {
        if (mCustomerViewModel == null)
        {
            mCustomerViewModel = new CustomerViewModel();
        }
        return mCustomerViewModel;
    }

    private final MutableLiveData<FirebaseUser> userInSession = new MutableLiveData<>();
    public LiveData<FirebaseUser> getUserSessionState(){
        return this.userInSession;
    }

}
