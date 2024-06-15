package com.example.carcab.AuthenticateFeature.AuthenticationViews.Abstractions;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.carcab.R;

public abstract class SwitchableFragment extends Fragment {

    public SwitchableFragment()
    {

    }
    protected void switchFragment(int containerViewId, Fragment fragment)
    {
        FragmentManager fragMan = getActivity().getSupportFragmentManager();
        FragmentTransaction fragTransact = fragMan.beginTransaction();

        fragTransact.replace(containerViewId, fragment);
        fragTransact.addToBackStack(null);
        fragTransact.commit();
    }

    protected void SetupBackPressedExit()
    {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finish();
            }
        });
    }


}
