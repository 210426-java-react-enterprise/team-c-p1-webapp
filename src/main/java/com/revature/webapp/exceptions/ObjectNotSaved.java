package com.revature.webapp.exceptions;

public class ObjectNotSaved extends RuntimeException{
    public ObjectNotSaved(){super("The object could not be saved into DB");}
}
