package com.example.carcab.UserPageFeature.Customers.Views.UserActivities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.carcab.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class UserActivity extends AppCompatActivity {
    private MaterialToolbar userAppBar;
    private NavigationView userNavView;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_userpage_customer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.user_drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViewReferences();
        addNavsListener();
        addNavViewListener();
    }

    private void addViewReferences()
    {
        userAppBar = findViewById(R.id.AppBar);
        userNavView = findViewById(R.id.user_nav_view);
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

    private void addNavViewListener()
    {
        userNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int[] selections = {R.id.nav_user_aboutus, R.id.nav_user_logout};
                boolean isOk = false;
                for (int selection : selections)
                {
                    Log.d("Nav-Drawer-Selection", item.getTitle().toString());
                    if (item.getItemId() == selection)
                    {
                        isOk = true;
                        item.setChecked(true);
                        break;
                    }
                }
                Toast.makeText(UserActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                drawerLayout.close();
                return isOk;
            }
        });
    }
}