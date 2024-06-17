package com.example.carcab.AuthenticateFeature.AuthenticationViews.AuthActivities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carcab.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        toAuthenticationScreen();
    }

    // TODO: Remove and modify whatever is necessary (e.g. modify/delete the activity
    // TODO: Whatever the changes is, be sure to check over the manifest file
    // Do modify/omit this when the application has a flow and navigation
    private void toAuthenticationScreen()
    {
        Intent intent = new Intent(MainActivity.this, Authentication.class);
        startActivity(intent);
        finish();
    }
}