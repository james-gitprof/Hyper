package com.example.carcab.UserPageFeature.BaseContextTemplates;

import static com.mapbox.maps.plugin.gestures.GesturesUtils.getGestures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import static com.mapbox.maps.plugin.locationcomponent.LocationComponentUtils.getLocationComponent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Models.Abstractions.User;
import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository.FirebaseConnector;
import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository.RegularAuth;
import com.example.carcab.Defaults.UserState;
import com.example.carcab.R;
import com.example.carcab.UserPageFeature.Location;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.LocationPuck2D;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.gestures.OnMoveListener;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener;

import java.util.function.Consumer;

public abstract class MapFragmentTemplate extends Fragment {
    protected Location location;
    protected Point point;
    protected MapView userMap;    protected Button requestButton;
    protected FloatingActionButton focusFab;
    protected OnIndicatorBearingChangedListener mapIndicatorBearingChange;
    protected OnIndicatorPositionChangedListener mapIndicatorPositionChange;
    protected OnMoveListener mapIndicatorMoved;
    protected Bitmap bitmap;
    protected AnnotationPlugin annotationPlugin;
    protected PointAnnotationManager pointAnnotationManager;

    protected void setupEssentialsReferences(MapView map, FloatingActionButton fab, Button button)
    {
        this.userMap = map;
        this.focusFab = fab;
        this.requestButton = button;
        this.location = new Location();
        focusFab.hide();
    }
    protected void setupEssentialsFirst(Context context, boolean driverMode, TextView txt)
    {
        //focusFab.hide();
        addBtnListener(txt, driverMode);
        userMapStyleSet(context);
        DatabaseActionSet(driverMode, txt);
    }

    protected void addBtnListener(TextView txt, boolean driverMode)
    {
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference rootNode = FirebaseConnector.getInstance().getFirebaseDatabaseInstance().child("Users");
                if (driverMode)
                {
                    txt.setText("Looking for customers...");
                    Button driverActiveBtn = requestButton;
                    driverActiveBtn.setEnabled(false);
                    rootNode.child("Drivers").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot subSnapshot : snapshot.getChildren())
                            {
                                if (subSnapshot.getKey().equals(FirebaseConnector.getInstance().getFirebaseAuthInstance().getCurrentUser().getUid()))
                                {
                                    DatabaseReference driverNode = subSnapshot.getRef();
                                    driverNode.updateChildren(UserState.getInstanceDefaults().USER_SEARCH_ACTIVE_STATE);
                                    setDatabaseDriverOrCustomerFoundListener(txt, driverMode);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else
                {
                    txt.setText("Looking for drivers...");
                    requestButton.setEnabled(false);
                    // Customers
                    rootNode.child("Customers")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot subSnapshot : snapshot.getChildren())
                                    {
                                        if (subSnapshot.getKey().equals(FirebaseConnector.getInstance().getFirebaseAuthInstance().getCurrentUser().getUid()))
                                        {
                                            DatabaseReference customerNode = subSnapshot.getRef();
                                            customerNode.updateChildren(UserState.getInstanceDefaults().USER_SEARCH_ACTIVE_STATE);
                                            setDatabaseDriverOrCustomerFoundListener(txt, driverMode);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
            }
        });
    }

    protected void selfChangedListener(boolean driverMode, TextView statusTextDisplay)
    {
        DatabaseReference rootNode = FirebaseConnector.getInstance().getFirebaseDatabaseInstance().child("Users");
        FirebaseUser user = FirebaseConnector.getInstance().getFirebaseAuthInstance().getCurrentUser();
        if(driverMode)
        {
            DatabaseReference selfDriver = rootNode.child("Drivers").child(user.getUid());
            selfDriver.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if(snapshot.getValue().toString().equals("false"))
                    {
                        requestButton.setEnabled(true);
                        statusTextDisplay.setText("Customer found! Please proceed to the location.");
                    }
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else
        {
            DatabaseReference selfCustomer = rootNode.child("Customers").child(user.getUid());
            selfCustomer.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if (snapshot.getValue().toString().equals("false"))
                    {
                        requestButton.setEnabled(true);
                        statusTextDisplay.setText("Driver found! Please wait until the driver arrives.");
                    }
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    protected void setDatabaseDriverOrCustomerFoundListener(TextView statusTextDisplay, boolean driverMode)
    {
        DatabaseReference rootNode = FirebaseConnector.getInstance().getFirebaseDatabaseInstance().child("Users");
        if (driverMode)
        {
            DatabaseReference customersNode = rootNode.child("Customers");
            customersNode.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    DataSnapshot child = snapshot;
                    Log.d("Driver-Matcher", child.getKey());
                    for (DataSnapshot subChild : child.getChildren())
                    {
                        if(subChild.getKey().equals("searchActive"))
                        {
                            if (subChild.getValue().toString().equals("true"))
                            {
                                Button driverBtn = requestButton;
                                // Set customer to default, which is false, since we already found them
                                snapshot.getRef().updateChildren(UserState.getInstanceDefaults().USER_SEARCH_INACTIVE_STATE);
                                // Set the driver to default as well
                                DatabaseReference driverHeadRef = rootNode.child("Drivers");
                                DatabaseReference driver = driverHeadRef.child(FirebaseConnector.getInstance().getFirebaseAuthInstance().getCurrentUser().getUid());

                                driver.updateChildren(UserState.getInstanceDefaults().USER_SEARCH_INACTIVE_STATE);
                                driverBtn.setEnabled(true);
                                statusTextDisplay.setText("Customer found! Please proceed to the location.");
                            }
                        }
                    }
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
        else
        {
            // Customer
            DatabaseReference driversNode = rootNode.child("Drivers");
            driversNode.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    DataSnapshot child = snapshot;
                    Log.d("Customer-Matcher", child.getKey());
                    for (DataSnapshot subChild : child.getChildren())
                    {
                        if(subChild.getKey().equals("searchActive"))
                        {
                            Log.d("Customer-Matcher", String.format("Found key %s, with a value of %s", subChild.getKey(), subChild.getValue().toString()));
                            if (subChild.getValue().toString().equals("true"))
                            {
                                // Set driver to default, which is false, since we already found them
                                snapshot.getRef().updateChildren(UserState.getInstanceDefaults().USER_SEARCH_INACTIVE_STATE);
                                // Set the customer to default as well
                                DatabaseReference customerHeadRef = rootNode.child("Customers");
                                DatabaseReference customer = customerHeadRef.child(FirebaseConnector.getInstance().getFirebaseAuthInstance().getCurrentUser().getUid());
                                customer.updateChildren(UserState.getInstanceDefaults().USER_SEARCH_INACTIVE_STATE);
                                requestButton.setEnabled(true);
                                statusTextDisplay.setText("Driver found! Please wait until the driver gets to you.");

                            }
                        }
                    }

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    protected void DatabaseActionSet(boolean driverMode, TextView statusTxt)
    {
        DatabaseReference rootNode = FirebaseConnector.getInstance().getFirebaseDatabaseInstance().child("Users");
        if (driverMode)
        {
            // Drivers side
            DatabaseReference customerNode = rootNode.child("Customers");
            customerNode.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else
        {
            // Customer side
            DatabaseReference driversNode = rootNode.child("Drivers");
            driversNode.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    for (DataSnapshot subSnapshot : snapshot.getChildren())
                    {
                        if (subSnapshot.getKey().equals("searchActive"))
                        {
                            if (subSnapshot.getValue().toString().equals("true"))
                            {

                            }
                        }
                    }
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    protected void userMapStyleSet(Context context)
    {
        userMap.getMapboxMap().loadStyleUri(Style.SATELLITE, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                userMap.getMapboxMap().setCamera(new CameraOptions.Builder().zoom(20.0).build());
                LocationComponentPlugin locationComponentPlugin = getLocationComponent(userMap);
                locationComponentPlugin.setEnabled(true);
                LocationPuck2D locationPuck2D = new LocationPuck2D();
                locationPuck2D.setBearingImage(AppCompatResources.getDrawable(context, R.drawable.baseline_my_location_24));
                locationComponentPlugin.setLocationPuck(locationPuck2D);
                locationComponentPlugin.addOnIndicatorPositionChangedListener(mapIndicatorPositionChange);
                locationComponentPlugin.addOnIndicatorBearingChangedListener(mapIndicatorBearingChange);
                getGestures(userMap).addOnMoveListener(mapIndicatorMoved);

                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.location_marker);
                annotationPlugin = AnnotationPluginImplKt.getAnnotations(userMap);
                pointAnnotationManager  = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, new AnnotationConfig());
                focusFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        locationComponentPlugin.addOnIndicatorBearingChangedListener(mapIndicatorBearingChange);
                        locationComponentPlugin.addOnIndicatorPositionChangedListener(mapIndicatorPositionChange);
                        getGestures(userMap).addOnMoveListener(mapIndicatorMoved);
                        focusFab.hide();
                    }
                });
            }
        });
    }

    protected void setupEssentialIndicatorListeners()
    {
        mapIndicatorBearingChange = new OnIndicatorBearingChangedListener() {
            @Override
            public void onIndicatorBearingChanged(double v) {
                userMap.getMapboxMap().setCamera(new CameraOptions.Builder().bearing(v).build());
            }
        };

        mapIndicatorPositionChange = new OnIndicatorPositionChangedListener() {
            @Override
            public void onIndicatorPositionChanged(@NonNull Point point) {
                userMap.getMapboxMap().setCamera(new CameraOptions.Builder().center(point).zoom(20.0).build());
                getGestures(userMap).setFocalPoint(userMap.getMapboxMap().pixelForCoordinate(point));
                point = point;
            }
        };

        mapIndicatorMoved = new OnMoveListener() {
            @Override
            public void onMoveBegin(@NonNull MoveGestureDetector moveGestureDetector) {
                getLocationComponent(userMap).removeOnIndicatorBearingChangedListener(mapIndicatorBearingChange);
                getLocationComponent(userMap).removeOnIndicatorPositionChangedListener(mapIndicatorPositionChange);
                getGestures(userMap).removeOnMoveListener(mapIndicatorMoved);
                focusFab.show();

            }

            @Override
            public boolean onMove(@NonNull MoveGestureDetector moveGestureDetector) {
                return false;
            }

            @Override
            public void onMoveEnd(@NonNull MoveGestureDetector moveGestureDetector) {

            }
        };
    }




}
