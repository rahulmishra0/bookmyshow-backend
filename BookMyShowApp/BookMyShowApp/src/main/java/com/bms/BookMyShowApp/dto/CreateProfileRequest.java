package com.bms.BookMyShowApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateProfileRequest(
        @NotNull @Size(max = 100) String name,
        @NotNull @Email @Size(max = 200) String email,
        @NotNull @Pattern(regexp = "^[6-9]\\d{9}$") String phone) {
}
