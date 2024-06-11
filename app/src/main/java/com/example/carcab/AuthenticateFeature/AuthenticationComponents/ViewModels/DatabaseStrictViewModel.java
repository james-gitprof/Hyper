package com.example.carcab.AuthenticateFeature.AuthenticationComponents.ViewModels;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DatabaseStrictViewModel extends ViewModel {
    private static DatabaseStrictViewModel mDBStrictViewModel;
    private DatabaseStrictViewModel()
    {
        mDBStrictViewModel = this;
    }

    public DatabaseStrictViewModel getInstance()
    {
        return mDBStrictViewModel;
    }

    public void updateFoundDriverInDB(boolean isFound)
    {
        driverState.setValue(isFound);
    }

    private final MutableLiveData<Boolean> driverState = new MutableLiveData<>();

    public LiveData<Boolean> getFoundDriverState()
    {
        return this.driverState;
    }
}
