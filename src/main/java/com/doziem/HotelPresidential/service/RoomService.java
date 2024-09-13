package com.doziem.HotelPresidential.service;

import com.doziem.HotelPresidential.exception.ResourceNotFoundException;
import com.doziem.HotelPresidential.model.Room;
import com.doziem.HotelPresidential.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService{
    private final RoomRepository roomRepository;

    @Override
    public Room addNewRoom(Long roomNumber, MultipartFile file, String roomType, BigDecimal roomPrice, String roomDescription) throws SQLException, IOException {

        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        room.setRoomDescription(roomDescription);

        if(!file.isEmpty()){
            byte[] photoBytes = file.getBytes();

            byte[] photoBlob = new byte[0];
            room.setPhoto(photoBytes);
        }
        return roomRepository.save(room);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomType();
    }


    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }


    @Override
    public byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException {
        Optional<Room> roomPhoto = roomRepository.findById(roomId);
        if (roomPhoto.isEmpty() ) {
            throw new ResourceNotFoundException("Sorry, Room not found ");
        }
//        byte[] photoBlob = roomPhoto.get().getPhoto();
//        if(photoBlob != null){
//            return photoBlob.getBytes(1,(int) photoBlob.length());
//        }
        return roomPhoto.get().getPhoto();
    }

    @Override
    public void deleteRoom(Long roomId) {
        Optional<Room> theRoom = roomRepository.findById(roomId);

        if(theRoom.isPresent()){
            roomRepository.deleteById(roomId);
        }
    }

    @Override
    public Room updateRoom(Long roomId,Long roomNumber,String roomDescription, String roomType, BigDecimal roomPrice, byte[] photoByte) {

        Room room = roomRepository.findById(roomId).orElseThrow(()->new ResourceNotFoundException("Room not Found"));

        if(roomType != null) room.setRoomType(roomType);
        if(roomType != null) room.setRoomNumber(roomNumber);
        if(roomType != null) room.setRoomDescription(roomDescription);
        if(roomPrice != null) room.setRoomPrice(roomPrice);
        if(photoByte != null) {
//            String base64Photo = room.getPhotoBase64();
            room.setPhoto(photoByte);
        }
        return roomRepository.save(room);
    }

    @Override
    public Optional<Room> getRoomById(Long roomId) {
        return Optional.of(roomRepository.findById(roomId).get());
    }

    @Override
    public List<Room> geAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        return roomRepository.findAvailableRoomsByDatesAndType(checkInDate,checkOutDate,roomType);
    }
}
