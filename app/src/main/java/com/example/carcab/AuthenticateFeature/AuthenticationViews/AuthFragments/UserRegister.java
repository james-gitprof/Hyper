package com.example.carcab.AuthenticateFeature.AuthenticationViews.AuthFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carcab.AuthenticateFeature.AuthenticationComponents.ViewModels.AuthStrictViewModel;
import com.example.carcab.AuthenticateFeature.AuthenticationViews.Abstractions.AuthFragment;
import com.example.carcab.BuildConfig;
import com.example.carcab.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class UserRegister extends AuthFragment {

    private List<MaterialCardView> registerTypeSelections;
    EditText registerEmailField;
    EditText registerPasswordField;
    EditText registerConfirmPasswordField;

    LinearProgressIndicator progressBar;
    public UserRegister() {
        // Required empty public constructor
        registerTypeSelections = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AuthStrictViewModel model = new ViewModelProvider(this).get(AuthStrictViewModel.class);
        SetupBackPressedExit();
        int[] cardViewIds = {R.id.driver_card, R.id.customer_card};
        initializeUIElements();

        setupTextListeners();
        setupRegisterCardListenersEnMasse(cardViewIds);
        setupButtonListeners();

        model.getExceptionState().observe(getViewLifecycleOwner(), e -> {
            progressBar.setVisibility(View.GONE);
            Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.Acknowledgement_Action, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // automatically closes the thing
                        }
                    })
                    .show();
        });

        model.getDevExceptionState().observe(getViewLifecycleOwner(), e -> {
            progressBar.setVisibility(View.GONE);
            Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.Acknowledgement_Action, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // automatically closes the thing
                        }
                    })
                    .show();
        });

        model.getUserSessionState().observe(getViewLifecycleOwner(), e -> {
            progressBar.setVisibility(View.GONE);
            clearUIFields();
            Snackbar.make(getView(), "Account successfully registered. Proceed to login to continue.", Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.Acknowledgement_Action, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // automatically closes the thing
                        }
                    })
                    .show();

        });
    }

    private void initializeUIElements()
    {
        registerEmailField = ((TextInputLayout) getView().findViewById(R.id.register_email_field)).getEditText();
        registerPasswordField = ((TextInputLayout) getView().findViewById(R.id.register_password_field)).getEditText();
        registerConfirmPasswordField = ((TextInputLayout) getView().findViewById(R.id.register_confirmpassword_field)).getEditText();
        progressBar = getView().findViewById(R.id.register_progress);
    }

    private void clearUIFields()
    {
        registerEmailField.setText("");
        registerPasswordField.setText("");
        registerConfirmPasswordField.setText("");
    }

    private void setupButtonListeners()
    {
        Button registerBtn = getView().findViewById(R.id.registerbtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                String email = registerEmailField.getText().toString();
                String password = registerPasswordField.getText().toString();
                String confirmPassword = registerConfirmPasswordField.getText().toString();
                String userType = getChosenUserType();

                try {
                    mAuthViewModel.initRegisterProcess(email, password, confirmPassword, userType, getString(R.string.Authentication_Card_Driver));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public String getChosenUserType()
    {

        for (MaterialCardView card : registerTypeSelections)
        {
            if (card.isChecked())
            {
                String userType = getView().findViewById(card.getId()).getTag().toString();
                return userType;
            }
        }
        return "";
    }

    private void setupTextListeners()
    {
        TextView tappableRegister = getView().findViewById(R.id.register_text_clickable);
        tappableRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(R.id.authFragmentContainer,new UserLogin());
            }
        });
    }

    private void setupRegisterCardListenersEnMasse(int[] cv)
    {
        for (int card_id : cv)
        {
            MaterialCardView registerTypeCard = getView().findViewById(card_id);
            registerTypeCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialCardView clickedView = getView().findViewById(v.getId());
                    Log.d("CUSTOMER-TYPE", clickedView.getTag().toString());
                    clickedView.setChecked(true);
                    for (MaterialCardView card : registerTypeSelections)
                    {
                        if (card.getId() != v.getId())
                        {
                            if (card.isChecked())
                            {
                                card.setChecked(false);
                            }
                        }
                    }
                }
            });
            registerTypeSelections.add(registerTypeCard);
        }
    }
}