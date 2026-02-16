package it.unicam.roombooker.repository;

import it.unicam.roombooker.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("""
        select r from Room r
        where (:minCapacity is null or r.capacity >= :minCapacity)
        and r.id not in (
            select res.room.id from Reservation res
            where res.status = 'CONFIRMED'
            and :start < res.endTime
            and :end > res.startTime
        )
    """)
    List<Room> findAvailableRooms(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("minCapacity") Integer minCapacity
    );
}