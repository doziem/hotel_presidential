package com.doziem.HotelPresidential.repository;

import com.doziem.HotelPresidential.model.Room;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

@Transactional
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("Select Distinct r.roomType From Room r")
    List<String> findDistinctRoomType();

@Query(" SELECT r FROM Room r " +
        " WHERE r.roomType LIKE %:roomType% " +
        " AND r.id NOT IN (" +
        "  SELECT br.room.id FROM BookedRoom br " +
        "  WHERE ((br.checkInDate <= :checkOutDate) AND (br.checkOutDate >= :checkInDate))" +
        ")")
    List<Room> findAvailableRoomsByDatesAndType( LocalDate checkInDate, LocalDate checkOutDate, String roomType);
}
