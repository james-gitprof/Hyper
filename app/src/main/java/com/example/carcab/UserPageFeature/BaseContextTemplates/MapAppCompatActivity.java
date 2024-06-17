package com.example.carcab.UserPageFeature.BaseContextTemplates;

import android.content.Context;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public abstract class MapAppCompatActivity extends AppCompatActivity {
    protected ActivityResultLauncher<String> activityResultLauncher;

    protected void setupEssentialFirstActivityLauncher(Context context)
    {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {
                if (o)
                {
                    Toast.makeText(context, "Permission granted.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
