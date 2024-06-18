package com.example.carcab.UserPageFeature.Customers.ViewModels;

import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository.FirebaseConnector;

public class CustomerViewModelHandler {

    private static CustomerViewModelHandler mCustomerViewModelHandler;
    private static CustomerViewModel mCustomerViewModel;

    private CustomerViewModelHandler()
    {
        mCustomerViewModel = CustomerViewModel.getInstance();
    }

    public static CustomerViewModelHandler getInstance()
    {
        if (mCustomerViewModelHandler == null)
        {
            mCustomerViewModelHandler = new CustomerViewModelHandler();
        }
        return mCustomerViewModelHandler;
    }

    public void fetchLoggedInUserData()
    {
        mCustomerViewModel.updateUserInSession(FirebaseConnector.getInstance().getFirebaseAuthInstance().getCurrentUser());

    }


}
