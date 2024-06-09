package com.example.carcab.AuthenticateFeature.AuthenticationViews.AuthFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carcab.AuthenticateFeature.AuthenticationComponents.ViewModels.AuthenticationViewModel;
import com.example.carcab.AuthenticateFeature.AuthenticationViews.Abstractions.AuthFragment;
import com.example.carcab.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class UserRegister extends AuthFragment {

    private List<MaterialCardView> registerTypeSelections;
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
        SetupBackPressedExit();
        int[] cardViewIds = {R.id.driver_card, R.id.customer_card};

        setupTextListeners();
        setupRegisterCardListenersEnMasse(cardViewIds);
        setupButtonListeners();
    }

    private void setupButtonListeners()
    {
        Button registerBtn = getView().findViewById(R.id.registerbtn);
        EditText registerEmailField = ((TextInputLayout) getView().findViewById(R.id.register_email_field)).getEditText();
        EditText registerPasswordField = ((TextInputLayout) getView().findViewById(R.id.register_password_field)).getEditText();
        EditText registerConfirmPasswordField = ((TextInputLayout) getView().findViewById(R.id.register_confirmpassword_field)).getEditText();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearProgressIndicator progressBar = getView().findViewById(R.id.register_progress);
                progressBar.setVisibility(View.VISIBLE);
                String email = registerEmailField.getText().toString();
                String password = registerPasswordField.getText().toString();
                String confirmPassword = registerConfirmPasswordField.getText().toString();
                String userType = "";
                for (MaterialCardView card : registerTypeSelections)
                {
                    if (card.isChecked())
                    {
                        userType = getView().findViewById(card.getId()).getTag().toString();
                        break;
                    }
                }
                try {
                    if (mAuthViewModel.initRegisterProcess(email, password, confirmPassword, userType, getString(R.string.Authentication_Card_Driver)))
                    {
                        Snackbar.make(getView(), "Account successfully created.", Snackbar.LENGTH_SHORT).show();
                        registerEmailField.setText("");
                        registerPasswordField.setText("");
                        registerConfirmPasswordField.setText("");
                    }
                    else
                    {
                        Snackbar.make(getView(), "Something went wrong. Please recheck your inputs.", Snackbar.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setupTextListeners()
    {
        TextView tappableRegister = getView().findViewById(R.id.register_text_clickable);
        tappableRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(new UserLogin());
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