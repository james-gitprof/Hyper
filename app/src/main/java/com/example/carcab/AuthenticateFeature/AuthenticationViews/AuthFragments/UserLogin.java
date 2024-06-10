package com.example.carcab.AuthenticateFeature.AuthenticationViews.AuthFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carcab.AuthenticateFeature.AuthenticationComponents.ViewModels.AuthStrictViewModel;
import com.example.carcab.AuthenticateFeature.AuthenticationComponents.ViewModels.AuthViewModelHandler;
import com.example.carcab.AuthenticateFeature.AuthenticationViews.Abstractions.AuthFragment;
import com.example.carcab.R;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class UserLogin extends AuthFragment {

    EditText email_field;
    EditText password_field;
    Button loginButton;

    LinearProgressIndicator loginProgress;
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
        AuthStrictViewModel viewModel = new ViewModelProvider(this).get(AuthStrictViewModel.class);
        initializeUIElements();
        setButtonListeners();
        viewModel.getLocalUserSessionState().observe(getViewLifecycleOwner(), e -> {
            loginProgress.setVisibility(View.GONE);
            // pass UserInfo to next activity
            if (e.isDriver())
            {
                Snackbar.make(getView(), "Driver found. Proceeding to Driver's page", Snackbar.LENGTH_SHORT)
                        .show();
            }
            else
            {
                Snackbar.make(getView(), "Regular customer found. Proceeding to Customer's page", Snackbar.LENGTH_SHORT)
                        .show();
            }
        });

        viewModel.getExceptionState().observe(getViewLifecycleOwner(), e -> {
            loginProgress.setVisibility(View.GONE);
            Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.Acknowledgement_Action), v -> {
                        // do nothing
                    })
                    .show();
        });


        viewModel.getDevExceptionState().observe(getViewLifecycleOwner(), e -> {
            loginProgress.setVisibility(View.GONE);
            Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.Acknowledgement_Action), v -> {
                        // do nothing
                    })
                    .show();
        });
        setTextListeners();

    }

    private void initializeUIElements()
    {
        email_field = ((TextInputLayout) getView().findViewById(R.id.login_email_field)).getEditText();
        password_field = ((TextInputLayout) getView().findViewById(R.id.login_password_field)).getEditText();
        loginButton = getView().findViewById(R.id.login_button);
        loginProgress = getView().findViewById(R.id.login_progress);
    }

    private void setTextListeners()
    {
        TextView tappableRegister = getView().findViewById(R.id.register_text_clickable);
        SetupBackPressedExit();
        tappableRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new UserRegister());
            }
        });
    }

    private void setButtonListeners()
    {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProgress.setVisibility(View.VISIBLE);
                AuthViewModelHandler.getInstance().initLoginProcess(email_field.getText().toString(), password_field.getText().toString());
            }
        });
    }
}