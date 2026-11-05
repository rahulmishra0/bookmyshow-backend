package com.bms.BookMyShowApp.dto;

import com.bms.BookMyShowApp.entity.Booking;
import com.bms.BookMyShowApp.entity.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BookingResponse(Long id, Long showId, Long profileId, String movieTitle, String theatreName,
                              String customerName, String customerEmail, String customerPhone, List<String> seatLabels,
                              BigDecimal totalAmount, BookingStatus status, LocalDateTime bookerAt) {
    public static BookingResponse from (Booking booking){
        return new BookingResponse(booking.getId(), booking.getShow().getId(),
                booking.getCustomer()==null ? null : booking.getCustomer().getId(),
                booking.getShow().getMovie().getTitle(),
                booking.getShow().getTheatre().getName(), booking.getCustomerName(), booking.getCustomerEmail(),
                booking.getCustomerPhone(), booking.getSeatLabels(), booking.getTotalAmount(),
                booking.getStatus(), booking.getBookedAt());
    }
}
