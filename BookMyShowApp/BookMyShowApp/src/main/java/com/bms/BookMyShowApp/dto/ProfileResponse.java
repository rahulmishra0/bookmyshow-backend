package com.bms.BookMyShowApp.dto;

import com.bms.BookMyShowApp.entity.Customer;
import org.springframework.context.annotation.Profile;

public record ProfileResponse(Long id, String name, String email, String phone) {
    public static ProfileResponse from (Customer customer){
        return new ProfileResponse(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone());
    }
}
