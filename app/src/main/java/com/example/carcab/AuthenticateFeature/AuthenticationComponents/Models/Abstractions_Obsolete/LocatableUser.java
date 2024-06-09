package com.example.carcab.AuthenticateFeature.AuthenticationComponents.Models.Abstractions_Obsolete;

public abstract class LocatableUser extends User implements IConfigurableLocatable {
    protected ILocatable userLocation;
    public LocatableUser(String email, String password) {
        super(email, password);

    }

    @Override
    public void setEntityLocation(ILocatable loc) {
        this.userLocation = loc;
    }

    @Override
    public ILocatable getEntityLocation() {
        return this.userLocation;
    }
}
