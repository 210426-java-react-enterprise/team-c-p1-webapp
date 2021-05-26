package com.revature.webapp.exceptions;

public class CustomerNotFound extends RuntimeException{
    public CustomerNotFound(){super("No customer was found with the provide credentials");}
}
