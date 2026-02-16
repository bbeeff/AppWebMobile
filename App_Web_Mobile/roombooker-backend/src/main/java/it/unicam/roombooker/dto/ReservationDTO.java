// src/main/java/it/unicam/roombooker/dto/ReservationDTO.java
package it.unicam.roombooker.dto;

import it.unicam.roombooker.model.ReservationStatus;
import java.time.LocalDateTime;

public class ReservationDTO {
    private Long id;
    private UserDTO user;
    private RoomDTO room;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ReservationStatus status;

    public ReservationDTO() {}

    public ReservationDTO(Long id, UserDTO user, RoomDTO room,
                          LocalDateTime startTime, LocalDateTime endTime,
                          ReservationStatus status) {
        this.id = id;
        this.user = user;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }

    public RoomDTO getRoom() { return room; }
    public void setRoom(RoomDTO room) { this.room = room; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
}