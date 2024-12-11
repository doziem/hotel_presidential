package com.doziem.HotelPresidential.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;



@Entity
@Setter
@Getter
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long roomNumber;

    private String roomType;
    private String roomDescription;

    private BigDecimal roomPrice;
    private Boolean isBooked = false;
    @Lob
    private byte[] photo;

    @OneToMany(mappedBy="room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookedRoom> bookings;


    public Room() {
        this.bookings = new ArrayList<>();
    }

    public void addBooking(BookedRoom booking){

        if(bookings == null){
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setRoom(this);

        isBooked = true;

        String bookingCode = RandomStringUtils.randomNumeric(10);
        booking.setBookingConfirmationCode(bookingCode);
    }

    // Base64 encoding of photo
    public String getPhotoBase64() {
        return Base64.getEncoder().encodeToString(this.photo);
    }

    // Base64 decoding of photo
    public void setPhotoBase64(String base64Photo) {
        this.photo = Base64.getDecoder().decode(base64Photo);
    }

}
