package com.bms.BookMyShowApp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateBookingRequest(
        @NotNull Long profileId,
        @NotEmpty @Size(max = 8) List<String> seatLabels
        ) {
}
