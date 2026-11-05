package com.bms.BookMyShowApp.service;

import com.bms.BookMyShowApp.dto.BookingResponse;
import com.bms.BookMyShowApp.dto.CreateBookingRequest;
import com.bms.BookMyShowApp.entity.Booking;
import com.bms.BookMyShowApp.entity.BookingStatus;
import com.bms.BookMyShowApp.entity.Show;
import com.bms.BookMyShowApp.entity.ShowSeat;
import com.bms.BookMyShowApp.repository.BookingRepository;
import com.bms.BookMyShowApp.repository.CustomerRepository;
import com.bms.BookMyShowApp.repository.ShowRepository;
import com.bms.BookMyShowApp.repository.ShowSeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Service
public class BookingService {

    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;

    public BookingService(ShowRepository showRepository, ShowSeatRepository showSeatRepository, BookingRepository bookingRepository, CustomerRepository customerRepository) {
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public BookingResponse book(Long showId, CreateBookingRequest request){
        Show show = showRepository.findById(showId).orElseThrow(()->new ResourceNotFoundException("Show not found"));
        var customer = customerRepository.findById(request.profileId())
                .orElseThrow(()->new ResourceNotFoundException("Profile not found"));

        List<String> labels = request.seatLabels().stream()
                .map(label->label.trim().toUpperCase(Locale.ROOT)).toList();

        if (labels.stream().distinct().count() != labels.size()){
            throw new SeatUnavailableException("Duplicate seat labels are not allowed");
        }

        List<ShowSeat> seats = showSeatRepository.findForUpdate(showId, labels);
        if (seats.size() != labels.size() || seats.stream().anyMatch(ShowSeat::isReserved)){
            throw new SeatUnavailableException("One or more selected seats are unavailable");
        }

        seats.forEach(ShowSeat::reserved);
        show.reserve(labels.size());

        BigDecimal total = show.getTicketPrice().multiply(BigDecimal.valueOf(labels.size()));

        // 🔥 CHANGE 2: Pehle booking PENDING state mein save karo
        Booking booking = new Booking(show, customer, total, labels);
        bookingRepository.save(booking);

        // ==========================================
        // Yahan future mein Payment Service call hoga.
        // Maan lo payment successful ho gaya:
        // ==========================================

        // 🔥 CHANGE 3: Payment success hone par status CONFIRMED karo
        booking.setStatus(BookingStatus.CONFIRMED);

        // Updated booking ko save karo (Optional hai kyunki @Transactional active hai)
        bookingRepository.save(booking);

        return BookingResponse.from(booking);
    }

    @Transactional
    public BookingResponse find(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()->new ResourceNotFoundException("Booking not found"));

        return BookingResponse.from(booking);
    }

    @Transactional
    public List<BookingResponse> findByProfileId(Long profileId){
        if (!customerRepository.existsById(profileId)){
            throw new ResourceNotFoundException("Profile not found");
        }

        return bookingRepository.findByCustomerIdOrderByBookedAtDesc(profileId).stream()
                .map(BookingResponse::from).toList();
    }

    @Transactional
    public BookingResponse cancel(Long bookingId, Long profileId){
        Booking booking = bookingRepository.findByIdAndCustomerId(bookingId, profileId)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));

        // Sirf CONFIRMED booking hi cancel ho sakti hai
        if (booking.getStatus() == BookingStatus.CONFIRMED){
            List<ShowSeat> seats = showSeatRepository.findForUpdate(booking.getShow().getId(), booking.getSeatLabels());
            seats.forEach(ShowSeat::release);
            booking.getShow().release(booking.getSeatLabels().size());
            booking.cancel();
        }
        return BookingResponse.from(booking);
    }
}