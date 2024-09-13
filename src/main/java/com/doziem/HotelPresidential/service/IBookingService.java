package com.doziem.HotelPresidential.service;

import com.doziem.HotelPresidential.model.BookedRoom;

import java.util.List;

public interface IBookingService {
    List<BookedRoom> getAllBookings();

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    String saveBooking(Long roomId, BookedRoom bookingRequest);

    void cancelBooking(Long bookingId);
}
