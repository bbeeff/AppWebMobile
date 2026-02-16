package it.unicam.roombooker.service;

import it.unicam.roombooker.model.Room;
import it.unicam.roombooker.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getAvailableRooms(
            LocalDateTime start,
            LocalDateTime end,
            Integer minCapacity
    ) {
        if (start == null || end == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "start and end are required"
            );
        }

        if (!start.isBefore(end)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid time interval"
            );
        }

        return roomRepository.findAvailableRooms(start, end, minCapacity);
    }
}