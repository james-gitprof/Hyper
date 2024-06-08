package com.example.carcab.CompAuthentication.Extras;

public final class Resource <R> {
    public record Success<R>(R result){}
    public record Failure<R extends Exception>(R exceptionResult){}

}
