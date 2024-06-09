package com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository;

import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Models.UserInfo;

public interface IAuthenticate {
    public boolean performRegister(UserInfo info);
    public boolean performLogin(UserInfo info);

}
