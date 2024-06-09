package com.example.carcab.AuthenticateFeature.AuthenticationViews.AuthFragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.carcab.AuthenticateFeature.AuthenticationViews.Abstractions.AuthFragment;
import com.example.carcab.R;
import com.google.android.material.card.MaterialCardView;

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
        setupRegisterCardListeners(cardViewIds);
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

    private void setupRegisterCardListeners(int[] cv)
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