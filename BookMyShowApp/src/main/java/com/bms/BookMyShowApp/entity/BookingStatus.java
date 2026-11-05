package com.bms.BookMyShowApp.entity;

public enum BookingStatus {

    PENDING,   // Jab user book karne ki request bhejta hai
    CONFIRMED, // Jab payment success hota hai
    CANCELLED, // Jab user cancel karta hai
    FAILED     // Jab payment fail ho jata hai

}