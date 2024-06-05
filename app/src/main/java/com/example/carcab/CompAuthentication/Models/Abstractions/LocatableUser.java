package com.example.carcab.CompAuthentication.Models.Abstractions;

import com.example.carcab.CompAuthentication.Models.Location;

public abstract class LocatableUser extends User implements IConfigurableLocatable {
    protected Location userLocation;
    public LocatableUser(String email, String password) {
        super(email, password);

    }

    @Override
    public void setEntityLocation(Location loc) {
        this.userLocation = loc;
    }

    @Override
    public Location getEntityLocation() {
        return this.userLocation;
    }
}
