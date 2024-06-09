package com.example.carcab.AuthenticateFeature.AuthenticationComponents.Models;

import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Models.Abstractions.IDrivable;

public class UserInfo {

    private boolean isDriver;
    private IDrivable vehicle;
    private String email, password;
    public UserInfo(String email, String password, boolean isDriver)
    {
        this.email = email;
        this.password = password;
        this.isDriver = isDriver;
    }

    public void setVehicle(IDrivable vehicle) throws Exception {
        if (!isDriver())
        {
            throw new Exception("User is not a driver!");
        }
        this.vehicle = vehicle;
    }

    public IDrivable getVehicle() throws Exception
    {
        if (!isDriver())
        {
            throw new Exception("Cannot set vehicle, User is not a driver!");
        }
        return vehicle;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean driver) {
        isDriver = driver;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
