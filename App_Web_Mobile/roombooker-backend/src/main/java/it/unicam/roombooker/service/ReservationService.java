package it.unicam.roombooker.service;

import it.unicam.roombooker.model.Reservation;
import it.unicam.roombooker.model.ReservationStatus;
import it.unicam.roombooker.model.Room;
import it.unicam.roombooker.model.User;
import it.unicam.roombooker.repository.ReservationRepository;
import it.unicam.roombooker.repository.RoomRepository;
import it.unicam.roombooker.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            RoomRepository roomRepository,
            UserRepository userRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getUserReservationsByEmail(String email) {
        return reservationRepository.findByUserEmail(email);
    }

    @Transactional
    public Reservation createReservationForEmail(
            String email,
            Long roomId,
            LocalDateTime start,
            LocalDateTime end
    ) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomId));

        // Check availability
        boolean hasConflict = reservationRepository.existsByRoomAndTimeRange(
                roomId, start, end, ReservationStatus.CONFIRMED
        );

        if (hasConflict) {
            throw new RuntimeException("Room is not available for the selected time");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setStartTime(start);
        reservation.setEndTime(end);
        reservation.setStatus(ReservationStatus.CONFIRMED);

        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation cancelReservation(Long reservationId, String email, boolean isAdmin) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        // Check ownership
        if (!isAdmin && !reservation.getUser().getEmail().equals(email)) {
            throw new RuntimeException("You can only cancel your own reservations");
        }

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new RuntimeException("Reservation already cancelled");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation updateReservation(
            Long reservationId,
            String email,
            Long newRoomId,
            LocalDateTime newStart,
            LocalDateTime newEnd
    ) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        // Check ownership
        if (!reservation.getUser().getEmail().equals(email)) {
            throw new RuntimeException("You can only modify your own reservations");
        }

        Room newRoom = roomRepository.findById(newRoomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Check availability (excluding current reservation)
        boolean hasConflict = reservationRepository.existsByRoomAndTimeRangeExcluding(
                newRoomId, newStart, newEnd, ReservationStatus.CONFIRMED, reservationId
        );

        if (hasConflict) {
            throw new RuntimeException("Room is not available for the selected time");
        }

        reservation.setRoom(newRoom);
        reservation.setStartTime(newStart);
        reservation.setEndTime(newEnd);

        return reservationRepository.save(reservation);
    }
}