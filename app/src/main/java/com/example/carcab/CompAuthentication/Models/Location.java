package com.example.carcab.CompAuthentication.Models;

import com.example.carcab.CompAuthentication.Models.Abstractions.IConfigurableLocatable;
import com.example.carcab.CompAuthentication.Models.Abstractions.ILocatable;

public class Location implements IConfigurableLocatable {
    @Override
    public void setEntityLocation(ILocatable loc) {

    }

    @Override
    public ILocatable getEntityLocation() {
        return null;
    }
}
