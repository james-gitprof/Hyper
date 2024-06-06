package com.example.carcab.CompAuthentication.Models.Abstractions;

import com.example.carcab.CompAuthentication.Models.Location;

public interface IConfigurableLocatable extends ILocatable
{
    public void setEntityLocation(ILocatable loc);
}
