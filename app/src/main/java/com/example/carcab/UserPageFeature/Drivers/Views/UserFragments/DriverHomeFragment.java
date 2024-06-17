package com.example.carcab.UserPageFeature.Drivers.Views.UserFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carcab.R;
import com.example.carcab.UserPageFeature.BaseContextTemplates.MapFragmentTemplate;

public class DriverHomeFragment extends MapFragmentTemplate {


    public DriverHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupEssentialsReferences(getView().findViewById(R.id.driverMapView),
                getView().findViewById(R.id.focusDriverLocation),
                getView().findViewById(R.id.btn_driver_active));
        setupEssentialIndicatorListeners();
        userMapStyleSet(getActivity());
        selfChangedListener(true, getView().findViewById(R.id.driver_txt_status));
        addBtnListener(getView().findViewById(R.id.driver_txt_status), true);
        //setDatabaseDriverOrCustomerFoundListener(getView().findViewById(R.id.driver_txt_status), true);
    }
}