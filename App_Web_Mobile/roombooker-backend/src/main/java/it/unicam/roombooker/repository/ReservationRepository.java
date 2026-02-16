package it.unicam.roombooker.repository;

import it.unicam.roombooker.model.Reservation;
import it.unicam.roombooker.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserEmail(String email);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM Reservation r " +
            "WHERE r.room.id = :roomId " +
            "AND r.status = :status " +
            "AND ((r.startTime < :end AND r.endTime > :start))")
    boolean existsByRoomAndTimeRange(
            @Param("roomId") Long roomId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("status") ReservationStatus status
    );

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM Reservation r " +
            "WHERE r.room.id = :roomId " +
            "AND r.status = :status " +
            "AND r.id != :excludeId " +
            "AND ((r.startTime < :end AND r.endTime > :start))")
    boolean existsByRoomAndTimeRangeExcluding(
            @Param("roomId") Long roomId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("status") ReservationStatus status,
            @Param("excludeId") Long excludeId
    );
}