package com.example.carcab.AuthenticateFeature.AuthenticationComponents.ViewModels;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Models.Abstractions.IDrivable;
import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Models.UserInfo;
import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository.FirebaseConnector;
import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository.IAuthenticate;
import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository.RegularAuth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class AuthViewModelHandler {

    // Helper class for AuthStrictViewModel.class

    private static AuthViewModelHandler mSelfAuthVM;
    private FirebaseUser userInSession;
    private UserInfo userMetadata;
    private IAuthenticate authenticator;
    private AuthViewModelHandler()
    {

    }

    public static AuthViewModelHandler getInstance()
    {
        if (mSelfAuthVM == null)
        {
            mSelfAuthVM = new AuthViewModelHandler();
            mSelfAuthVM.setAuthenticator(RegularAuth.getInstance());
        }
        return mSelfAuthVM;
    }

    public void initLoginProcess(String email, String password)
    {
        UserInfo userData = new UserInfo(email, password);
        boolean validationResult = !(email.isBlank() || password.isBlank()) ? true : false;
        if (validationResult == true)
        {
            getAuthenticator().performLogin(userData);
        }
        else
        {
            raiseMyErrorState(new Exception("Invalid or missing fields. Please check your inputs and try again."));
        }
    }

    public void initRegisterProcess(String email, String password, String password_2, String userType, String comparableConstant) throws Exception {
        if (password.hashCode() == password_2.hashCode() && !(password.isBlank() || password_2.isBlank() || userType.isBlank() ||comparableConstant.isBlank()))
        {
            boolean isDriver = userType.equalsIgnoreCase(comparableConstant) ? true : false;
            UserInfo user = new UserInfo(email, password, isDriver);
            if (isDriver) {
                user.setVehicle(new IDrivable() {
                    @Override
                    public String getVehicleType() {
                        return "Not configured";
                    }
                });
            }
            userMetadata = user;
            boolean status = getAuthenticator().performRegister(user);
            if (status)
            {
                userInSession = FirebaseConnector.getInstance().getFirebaseAuthInstance().getCurrentUser();
            }
        }
        else
        {
            raiseMyErrorState(new Exception("Invalid fields. Double-check your inputs."));
        }
    }

    public void raiseRegistrationSuccess(FirebaseUser user)
    {
        AuthStrictViewModel.getInstance().updateUserSessionState(user);
    }

    public void raiseMyErrorState(Exception e)
    {
        AuthStrictViewModel.getInstance().updateMyExceptionState(e);
    }

    public void raiseAuthenticationException(Exception e)
    {
        AuthStrictViewModel.getInstance().updateExceptionState(e);
    }

    private void setAuthenticator(IAuthenticate auth)
    {
        this.authenticator = auth;
    }

    private IAuthenticate getAuthenticator()
    {
        return this.authenticator;
    }


}
