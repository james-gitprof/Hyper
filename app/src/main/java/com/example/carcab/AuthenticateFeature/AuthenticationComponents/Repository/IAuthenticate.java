package com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository;

import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Models.UserInfo;

public interface IAuthenticate {
    public boolean performRegister(UserInfo info);
    public boolean performLogin(UserInfo info);

    public void performSignOut();
    /*
    Any methods that uses this must have AuthChanged listener in their
    activities/fragments or whatever to proceed to appropriate screen
     */
}
