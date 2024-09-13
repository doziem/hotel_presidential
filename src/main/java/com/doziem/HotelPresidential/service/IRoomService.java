package com.doziem.HotelPresidential.service;

import com.doziem.HotelPresidential.model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IRoomService {
    Room addNewRoom(Long roomNumber, MultipartFile file, String roomType, BigDecimal roomPrice, String roomDescription) throws SQLException, IOException;

    List<String> getAllRoomTypes();


    List<Room> getAllRooms();

    byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException;

    void deleteRoom(Long roomId);

    Room updateRoom(Long roomId,Long roomNumber,String roomDescription, String roomType, BigDecimal roomPrice, byte[] photoByte);

    Optional<Room> getRoomById(Long roomId);

    List<Room> geAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
}
