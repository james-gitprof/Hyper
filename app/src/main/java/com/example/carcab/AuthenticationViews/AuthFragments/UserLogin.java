package com.example.carcab.AuthenticationViews.AuthFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.carcab.AuthenticationViews.Abstractions.AuthFragment;
import com.example.carcab.CompAuthentication.ViewModels.AuthenticationViewModel;
import com.example.carcab.AuthenticationViews.Abstractions.SwitchableFragment;
import com.example.carcab.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserLogin extends AuthFragment {

    public UserLogin() {
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
        return inflater.inflate(R.layout.fragment_user_login, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tappableRegister = getView().findViewById(R.id.register_text_clickable);
        SetupBackPressedExit();
        tappableRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new UserRegister());
            }
        });
    }
}