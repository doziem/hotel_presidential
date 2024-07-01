package com.doziem.HotelPresidential.repository;

import com.doziem.HotelPresidential.model.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long> {
}
