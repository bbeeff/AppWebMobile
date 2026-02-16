// src/main/java/it/unicam/roombooker/dto/DtoMapper.java
package it.unicam.roombooker.dto;

import it.unicam.roombooker.model.Reservation;
import it.unicam.roombooker.model.Room;
import it.unicam.roombooker.model.User;

public class DtoMapper {

    private DtoMapper() {}

    public static UserDTO toUserDTO(User u) {
        if (u == null) return null;
        return new UserDTO(u.getId(), u.getEmail(), u.getRole());
    }

    public static RoomDTO toRoomDTO(Room r) {
        if (r == null) return null;
        return new RoomDTO(r.getId(), r.getName(), r.getBuilding(), r.getCapacity());
    }

    public static ReservationDTO toReservationDTO(Reservation res) {
        if (res == null) return null;
        return new ReservationDTO(
                res.getId(),
                toUserDTO(res.getUser()),
                toRoomDTO(res.getRoom()),
                res.getStartTime(),
                res.getEndTime(),
                res.getStatus()
        );
    }
}