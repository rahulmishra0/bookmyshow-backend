package com.bms.BookMyShowApp.service;

public class ProfileConflictException extends RuntimeException{
    public ProfileConflictException(String msg){
        super(msg);
    }
}
