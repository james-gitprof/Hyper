package com.example.carcab.UserPageFeature.Drivers.Views.UserActivities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository.IAuthenticate;
import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository.RegularAuth;
import com.example.carcab.AuthenticateFeature.AuthenticationViews.AuthActivities.Authentication;
import com.example.carcab.R;
import com.example.carcab.UserPageFeature.Customers.ViewModels.UserViewModel;
import com.example.carcab.UserPageFeature.Customers.ViewModels.UserViewModelHandler;
import com.example.carcab.UserPageFeature.BaseContextTemplates.MapAppCompatActivity;
import com.example.carcab.UserPageFeature.Drivers.Views.UserFragments.DriverHomeFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class DriverActivity extends MapAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_driver);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.driver_drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViewReferences();
        initializeEssentialComp();
        addNavsListener();
        defaultMenuItemChecked();
        addNavViewListener();
        setupActivityLauncherPerms();
    }

    private void setupActivityLauncherPerms()
    {
        if (ActivityCompat.checkSelfPermission(DriverActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            setupEssentialFirstActivityLauncher(DriverActivity.this);
            activityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private UserViewModel mDriverViewModel;
    private UserViewModelHandler mDriverViewModelHandler;

    private Context driverActivity;

    private IAuthenticate authenticator;

    private MaterialToolbar userAppBar;

    private NavigationView userNavView;

    private TextView displayNameText;

    private DrawerLayout drawerLayout;

    private void initializeEssentialComp()
    {
        mDriverViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mDriverViewModelHandler = UserViewModelHandler.getInstance();
        driverActivity = this;
        authenticator = RegularAuth.getInstance();
    }

    private void addViewReferences() {
        userAppBar = findViewById(R.id.DriverAppBar);
        userNavView = findViewById(R.id.driver_nav_view);
        displayNameText = findViewById(R.id.nav_loggeduser_display_name);
    }

    private void addNavsListener()
    {
        userAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout = findViewById(R.id.driver_drawer_layout);
                drawerLayout.open();
            }
        });
    }

    private void activityFragmentSwitcher(int containerViewId, Fragment fragment)
    {
        FragmentManager activityFragMan = getSupportFragmentManager();
        FragmentTransaction fragTransact = activityFragMan.beginTransaction();
        fragTransact.replace(containerViewId, fragment);
        fragTransact.addToBackStack(null);
        fragTransact.commit();
    }

    private void defaultMenuItemChecked()
    {
        final int DEFAULT_CHECKED_ITEM = R.id.driver_nav_find_a_customer;
        userNavView.setCheckedItem(DEFAULT_CHECKED_ITEM);
    }

    private void addNavViewListener() {
        userNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean isOk = false;
                if (item.getItemId() == R.id.driver_nav_find_a_customer) {
                    activityFragmentSwitcher(R.id.userDriverFragmentContainerView, new DriverHomeFragment());
                }
                if (item.getItemId() == R.id.driver_nav_user_logout)
                {
                    authenticator.performSignOut();
                    Intent intent = new Intent(DriverActivity.this, Authentication.class);
                    startActivity(intent);
                    Activity currentActivity = (Activity) driverActivity;
                    currentActivity.finish();
                    return true;
                }
                isOk = true;
                item.setChecked(true);
                drawerLayout.close();
                return isOk;
            }
        });
    }
}