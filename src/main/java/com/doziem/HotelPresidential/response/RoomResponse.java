package com.doziem.HotelPresidential.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
public class RoomResponse {

    private Long id;

    private Long roomNumber;

    private String roomDescription;

    private String roomType;


    private BigDecimal roomPrice;

    private Boolean isBooked = false;

    private String photo;

//    private List<BookingResponse> bookings;

    public RoomResponse(Long id,Long roomNumber, String roomDescription, String roomType, BigDecimal roomPrice) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomDescription = roomDescription;
        this.roomType = roomType;
        this.roomPrice = roomPrice;

    }

    public RoomResponse(
                        Long id,
                        Long roomNumber,
                        String roomDescription,
                        String roomType,
                        BigDecimal roomPrice,
                        Boolean isBooked,
                        byte[] photoBytes

//                        List<BookingResponse> bookings
    ) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomDescription = roomDescription;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.isBooked = isBooked;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes):null;

//        this.bookings = bookings;


    }
}
