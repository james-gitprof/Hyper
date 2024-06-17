package com.example.carcab.UserPageFeature.Customers.Views.UserActivities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository.IAuthenticate;
import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository.RegularAuth;
import com.example.carcab.AuthenticateFeature.AuthenticationViews.AuthActivities.Authentication;
import com.example.carcab.R;
import com.example.carcab.UserPageFeature.Customers.ViewModels.UserViewModel;
import com.example.carcab.UserPageFeature.Customers.ViewModels.UserViewModelHandler;
import com.example.carcab.UserPageFeature.BaseContextTemplates.MapAppCompatActivity;
import com.example.carcab.UserPageFeature.Customers.Views.UserFragments.CustomerHome;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;

public class CustomerActivity extends MapAppCompatActivity {
    private MaterialToolbar userAppBar;
    private NavigationView userNavView;
    private DrawerLayout drawerLayout;
    private UserViewModel mUserViewModel;

    private TextView displayNameText;
    private UserViewModelHandler VMHandler;

    private IAuthenticate authenticator;

    private Context userActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //MapboxOptions.accessToken = BuildConfig.MAPBOX_PUBLIC_API_KEY;

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_userpage_customer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.user_drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeEssentialComp();
        setupActivityLauncherPerms();
        addViewReferences();
        addNavsListener();
        addNavViewListener();
        defaultMenuItemChecked();
        mUserViewModel.getUserSessionState().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Toast.makeText(userActivity, "Welcome back.", Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(
                            getWindow().getDecorView().getRootView(),
                            "Seems like there was an error with communicating with the server.",
                            Snackbar.LENGTH_SHORT
                    ).show();

                }
            }
        });
    }

    private void setupActivityLauncherPerms()
    {
        if (ActivityCompat.checkSelfPermission(CustomerActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            setupEssentialFirstActivityLauncher(CustomerActivity.this);
            activityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    public UserViewModel getActivityViewModel() {
        return this.mUserViewModel;
    }

    @Override
    protected void onStart() {
        super.onStart();
        VMHandler.fetchLoggedInUserData();
    }

    private void initializeEssentialComp()
    {
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        VMHandler = UserViewModelHandler.getInstance();
        userActivity = this;
        authenticator = RegularAuth.getInstance();
    }

    private void addViewReferences() {
        userAppBar = findViewById(R.id.AppBar);
        userNavView = findViewById(R.id.user_nav_view);
        displayNameText = findViewById(R.id.nav_loggeduser_display_name);
    }

    private void addNavsListener()
    {
        userAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout = findViewById(R.id.user_drawer_layout);
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
        final int DEFAULT_CHECKED_ITEM = R.id.nav_find_a_driver;
        userNavView.setCheckedItem(DEFAULT_CHECKED_ITEM);
    }

    private void addNavViewListener() {
        userNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean isOk = false;
                if (item.getItemId() == R.id.nav_find_a_driver) {
                    activityFragmentSwitcher(R.id.userCustomerFragmentContainerView, new CustomerHome());
                }
                if (item.getItemId() == R.id.nav_user_logout)
                {
                    authenticator.performSignOut();
                    Intent intent = new Intent(userActivity, Authentication.class);
                    startActivity(intent);
                    Activity currentActivity = (Activity) userActivity;
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