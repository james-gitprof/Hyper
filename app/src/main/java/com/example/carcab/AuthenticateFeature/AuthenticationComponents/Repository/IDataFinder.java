package com.example.carcab.AuthenticateFeature.AuthenticationComponents.Repository;

public interface IDataFinder<E> {
    public boolean isAvailable(E data);
}
