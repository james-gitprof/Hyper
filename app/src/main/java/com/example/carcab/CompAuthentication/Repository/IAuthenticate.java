package com.example.carcab.CompAuthentication.Repository;

import com.example.carcab.CompAuthentication.Extras.Resource;
import com.google.firebase.auth.FirebaseUser;

import javax.annotation.Nullable;

public interface IAuthenticate {
    @Nullable
    FirebaseUser userInSession = null;
    public Resource<FirebaseUser> performRegister(String email, String password);
    public Resource<FirebaseUser> performLogin(String email, String password);
}
