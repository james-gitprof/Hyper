package com.example.carcab.AuthenticateFeature.AuthenticationComponents.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

public class AuthStrictViewModel extends ViewModel {

    // Stricly enforces the idea of ViewModel as per the ViewModel android developer documentation
    // AuathenticationViewModel on the other hand is somewhat lax and doesn't follow the proper way completely...
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
        Log.d("AuthViewModel-Heartbeat", "Ambatugetdestroyed!! AAAAAHHH!");
        super.onCleared();
    }

    public void updateExceptionState(Exception e)
    {
        exceptionState.setValue(e);
    }
    public void updateMyExceptionState(Exception e) { devExceptionState.setValue(e);}
    public void updateLocalUserSessionState(FirebaseUser user)
    {
        localUserSessionState.setValue(user);
    }
    private final MutableLiveData<Exception> exceptionState = new MutableLiveData<>();

    private final MutableLiveData<FirebaseUser> localUserSessionState = new MutableLiveData<>();
    private final MutableLiveData<Exception> devExceptionState = new MutableLiveData<>();
    public LiveData<Exception> getDevExceptionState() {
        return this.devExceptionState;
    }
    public LiveData<FirebaseUser> getLocalUserSessionState() {
        return localUserSessionState;
    }
    public LiveData<Exception> getExceptionState()
    {
        return exceptionState;
    }
}
