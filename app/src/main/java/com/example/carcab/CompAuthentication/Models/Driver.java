package com.example.carcab.CompAuthentication.Models;

import com.example.carcab.CompAuthentication.Models.Abstractions.IConfigurableLocatable;
import com.example.carcab.CompAuthentication.Models.Abstractions.IDrivable;
import com.example.carcab.CompAuthentication.Models.Abstractions.ILocatable;
import com.example.carcab.CompAuthentication.Models.Abstractions.LocatableUser;
import com.example.carcab.CompAuthentication.Models.Abstractions.User;

public class Driver extends LocatableUser {

    private IDrivable driverCar;

    public Driver(String email, String password) {
        super(email, password);
    }

    public IDrivable getDriverCar()
    {
        return this.driverCar;
    }
}
