package com.example.carcab.AuthenticateFeature.AuthenticationComponents.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Models.UserInfo;
import com.google.firebase.auth.FirebaseUser;

public class AuthStrictViewModel extends ViewModel {

    // Stricly enforces the idea of ViewModel as per the ViewModel android developer documentation
    // AuthenticationViewModelHandler on the other hand is somewhat lax and doesn't follow the proper way completely...
    // I do think it still meets the role of a ViewModel, just in a different way
    // You think im wrong? Take it up to the Issues tab in the github and lets talk about it
    private static AuthStrictViewModel mStrictViewModel;
    private AuthStrictViewModel()
    {
        mStrictViewModel = this;
    }
    public static AuthStrictViewModel getInstance()
    {
        return mStrictViewModel;
    }

    @Override
    protected void onCleared() {
        Log.d("AUTH-VIEWMODEL-HEARTBEAT", "Ambatugetdestroyed!! AAAAAHHH!!");
        super.onCleared();
    }

    public void updateExceptionState(Exception e)
    {
        exceptionState.setValue(e);
    }
    public void updateMyExceptionState(Exception e) { devExceptionState.setValue(e);}
    public void updateUserSessionState(FirebaseUser user)
    {
        userSessionState.setValue(user);
    }

    public void updateLocalUserSessionState(UserInfo user)
    {
        localUserSessionState.setValue(user);
    }

    private final MutableLiveData<Exception> exceptionState = new MutableLiveData<>();
    private final MutableLiveData<UserInfo> localUserSessionState = new MutableLiveData<>();
    private final MutableLiveData<FirebaseUser> userSessionState = new MutableLiveData<>();
    private final MutableLiveData<Exception> devExceptionState = new MutableLiveData<>();
    public LiveData<Exception> getDevExceptionState() {
        return this.devExceptionState;
    }
    public LiveData<FirebaseUser> getUserSessionState() {
        return userSessionState;
    }
    public LiveData<Exception> getExceptionState()
    {
        return exceptionState;
    }

    public LiveData<UserInfo> getLocalUserSessionState() { return localUserSessionState;}
}
