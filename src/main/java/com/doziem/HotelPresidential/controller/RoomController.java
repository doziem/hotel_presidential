package com.doziem.HotelPresidential.controller;


import com.doziem.HotelPresidential.exception.ResourceNotFoundException;
import com.doziem.HotelPresidential.model.BookedRoom;
import com.doziem.HotelPresidential.model.Room;
import com.doziem.HotelPresidential.response.RoomResponse;
import com.doziem.HotelPresidential.service.BookingService;
import com.doziem.HotelPresidential.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final IRoomService roomService;
    private final BookingService bookingService;

    @PostMapping("/add/new-room")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoomResponse> addNewRoom(
            @RequestParam("roomNumber") Long roomNumber,
            @RequestParam("roomDescription") String roomDescription,
           @RequestParam("photo") MultipartFile photo,
           @RequestParam("roomType") String roomType,
           @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, IOException {

        Room savedRoom = roomService.addNewRoom(roomNumber,photo,roomType,roomPrice,roomDescription);

        RoomResponse response = new RoomResponse(
                savedRoom.getId(),
                savedRoom.getRoomNumber(),
                savedRoom.getRoomDescription(),
                savedRoom.getRoomType(),
                savedRoom.getRoomPrice()

        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/room/type")
    public List<String> getRoomTypes(){
        return roomService.getAllRoomTypes();
    }

    @GetMapping("/room/all-rooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> roomResponses = new ArrayList<>();

        for(Room room :rooms){
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            if(photoBytes != null && photoBytes.length > 0){
                String base64Photo = Base64.encodeBase64String(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);

                roomResponse.setPhoto(base64Photo);
                roomResponses.add(roomResponse);
            }
        }
    return ResponseEntity.ok(roomResponses);
    }

    @DeleteMapping("/delete/room/{roomId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId){
        roomService.deleteRoom(roomId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
@PutMapping("/update/{roomId}")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long roomId,
                                                   @RequestParam(required = false) Long roomNumber,
                                                   @RequestParam(required = false) String roomDescription,
                                                   @RequestParam(required = false) String roomType,
                                                   @RequestParam(required = false) BigDecimal roomPrice,
                                                   @RequestParam(required = false) MultipartFile photo) throws IOException, SQLException {

       byte[] photoByte = photo != null && !photo.isEmpty()? photo.getBytes(): roomService.getRoomPhotoByRoomId(roomId);

   Room theRoom = roomService.updateRoom(roomId, roomNumber, roomDescription,roomType,roomPrice,photoByte);
   theRoom.setPhoto(photoByte);

   RoomResponse response = getRoomResponse(theRoom);

   return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<Optional<RoomResponse>> getRoomById(@PathVariable Long roomId){
        Optional<Room> theRoom = roomService.getRoomById(roomId);

        return theRoom.map(room -> {
            RoomResponse roomResponse = getRoomResponse(room);
            return ResponseEntity.ok(Optional.of(roomResponse));
        }).orElseThrow(()-> new ResourceNotFoundException("Room not Found"));
    }

    @GetMapping("/available-rooms")
    public ResponseEntity<List<RoomResponse>> getAvailableRooms(
            @RequestParam("checkInDate") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)LocalDate checkInDate,
            @RequestParam("checkOutDate") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)LocalDate checkOutDate,
            @RequestParam("roomType") String roomType
            ) throws SQLException {
        List<Room> availableRooms = roomService.geAvailableRooms(checkInDate,checkOutDate,roomType);
        List<RoomResponse> roomResponses = new ArrayList<>();

        for (Room room: availableRooms) {
            byte[] photoByte = roomService.getRoomPhotoByRoomId(room.getId());
            if(photoByte != null && photoByte.length >0){
                String photoBase64 = Base64.encodeBase64String(photoByte);
                RoomResponse roomResponse = getRoomResponse(room);

                roomResponse.setPhoto(photoBase64);
                roomResponses.add(roomResponse);
            }
        }

        if(roomResponses.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(roomResponses);
        }
    }

    private RoomResponse getRoomResponse(Room room) {
//        List<BookedRoom> bookings = getAllBookingsByRoomId(room.getId());

//        List<BookingResponse> bookingResponses = bookings
//                                   .stream()
//                                    .map(booking -> new BookingResponse(booking.getBookingId(),
//                                     booking.getCheckInDate(),
//                                     booking.getCheckOutDate(),
//                                     booking.getConfirmationCode())).toList();

        byte[] photoByte = null;

        byte[] photoBlob = room.getPhoto();
//        if(photoBlob != null){
//            try {
//               photoByte = photoBlob.getBytes(1,(int) photoBlob.length());
//            }catch (SQLException e){
//                throw new PhotoRetrievingException("Error Retrieving Room Photo");
//            }
//        }

        return new RoomResponse(room.getId(),
                room.getRoomNumber(),
                room.getRoomDescription(),
                room.getRoomType(),
                room.getRoomPrice(),
                room.getIsBooked(),
                photoBlob

        );
    }

    private List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookingService.getAllBookedRoomByRoomId(roomId);
    };
}
