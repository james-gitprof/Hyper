package com.example.carcab.AuthenticateFeature.AuthenticationComponents.Models.Abstractions;

public interface IConfigurableLocatable extends ILocatable
{
    public void setEntityLocation(ILocatable loc);
}
