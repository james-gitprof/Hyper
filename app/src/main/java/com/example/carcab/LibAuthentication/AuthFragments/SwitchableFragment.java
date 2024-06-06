package com.example.carcab.LibAuthentication.AuthFragments;

import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.carcab.MainActivity;
import com.example.carcab.R;

public class SwitchableFragment extends Fragment {


    public SwitchableFragment()
    {
    }
    protected void switchFragment(Fragment fragment)
    {
        FragmentManager fragMan = getActivity().getSupportFragmentManager();
        FragmentTransaction fragTransact = fragMan.beginTransaction();

        fragTransact.replace(R.id.authFragmentContainer, fragment);
        // fragTransact.addToBackStack(null);
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