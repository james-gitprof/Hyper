package com.example.carcab.CompAuthentication.Models;

import com.example.carcab.CompAuthentication.Models.Abstractions.*;
public class Customer extends LocatableUser{
    public Customer(String email, String password) {
        super(email, password);
    }
}
