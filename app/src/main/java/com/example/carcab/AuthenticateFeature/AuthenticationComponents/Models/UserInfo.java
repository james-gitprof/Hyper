package com.example.carcab.AuthenticateFeature.AuthenticationComponents.Models;

import com.example.carcab.AuthenticateFeature.AuthenticationComponents.Models.Abstractions.IDrivable;

public class UserInfo {

    private boolean isDriver;
    private IDrivable vehicle;
    private java.lang.String email, password;
    public UserInfo(java.lang.String email, java.lang.String password, boolean isDriver)
    {
        this.email = email;
        this.password = password;
        this.isDriver = isDriver;
        this.vehicle = new IDrivable() {
            @Override
            public String getVehicleType() {
                return "Not configured";
            }
        };
    }

    public UserInfo(java.lang.String email, java.lang.String password)
    {
        this.email = email;
        this.password = password;
        this.isDriver = false;
        this.vehicle = new IDrivable() {
            @Override
            public String getVehicleType() {
                return "N/A";
            }
        };
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

    public java.lang.String getEmail() {
        return email;
    }

    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    public java.lang.String getPassword() {
        return password;
    }

    public void setPassword(java.lang.String password) {
        this.password = password;
    }
}
