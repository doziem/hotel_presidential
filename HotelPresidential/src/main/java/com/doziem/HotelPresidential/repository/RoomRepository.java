package com.doziem.HotelPresidential.repository;

import com.doziem.HotelPresidential.model.Room;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Transactional
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("Select Distinct r.roomType From Room r")
    List<String> findDistinctRoomType();
}
