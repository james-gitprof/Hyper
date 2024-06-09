package com.example.carcab.AuthenticateFeature.AuthenticationComponents.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Models.UserInfo;
import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository.FirebaseConnector;
import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository.IAuthenticate;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationViewModel extends ViewModel {
    /*
    WTF IS THIS?

    Read here, boah.
    https://developer.android.com/topic/libraries/architecture/viewmodel
     */

    /*
    WARNING: NOT THREAD-SAFE!!
    Modify this singleton class when working with threads if its needed
    I don't think its gonna be needed for this case but you never know...
     */
    private static AuthenticationViewModel mSelfAuthVM;
    private static FirebaseConnector mFirebase;
    private final MutableLiveData<FirebaseUser> userInSession = new MutableLiveData<>();
    private IAuthenticate authenticator;
    private AuthenticationViewModel()
    {
        // When initialized, provide connection to firebase repository through connector
        mFirebase = FirebaseConnector.getInstance();

    }

    public static AuthenticationViewModel getInstance()
    {
        if (mSelfAuthVM == null)
        {
            mSelfAuthVM = new AuthenticationViewModel();
        }
        return mSelfAuthVM;
    }

    private boolean initLoginProcess()
    {
        return false;
    }

    private boolean initRegisterProcess(String email, String password, String password_2, String userType, String comparableConstant)
    {
        // validate data
        if (password.hashCode() == password_2.hashCode())
        {
            boolean isDriver = userType.equalsIgnoreCase(comparableConstant) ? true : false;
            UserInfo user = new UserInfo(email, password, isDriver);
            if (getAuthenticator().performRegister(user))
            {
                userInSession.setValue(FirebaseConnector.getInstance().getFirebaseAuthInstance().getCurrentUser());
                return true;
            }
        }
        return false;
    }

    private void setAuthenticator(IAuthenticate auth)
    {
        this.authenticator = auth;
    }

    private IAuthenticate getAuthenticator()
    {
        return this.authenticator;
    }

    public LiveData<FirebaseUser> getUserInSession() // use observer to get state at any time
    {
        return userInSession;
    }

}
