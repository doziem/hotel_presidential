package com.doziem.HotelPresidential.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private Long BookingId;

    private Long roomNumber;

    private String roomDescription;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;


    private String guestFullName;


    private  String guestEmail;

    private int numOfAdults;

    private int numOfChildren;


    private int totalNumOfGuest;

    private String confirmationCode;


    private RoomResponse room;

    public BookingResponse(Long BookingId, Long roomNumber, String roomDescription, LocalDate checkInDate, LocalDate checkOutDate, String confirmationCode) {
        this.BookingId = BookingId;
        this.roomNumber = roomNumber;
        this.roomDescription = roomDescription;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.confirmationCode = confirmationCode;
    }
}
