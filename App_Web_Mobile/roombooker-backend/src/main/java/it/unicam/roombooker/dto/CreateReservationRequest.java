package it.unicam.roombooker.dto;

import java.time.LocalDateTime;

public class CreateReservationRequest {
    private Long roomId;
    private LocalDateTime start;
    private LocalDateTime end;

    public CreateReservationRequest() {}

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }

    public LocalDateTime getStart() { return start; }
    public void setStart(LocalDateTime start) { this.start = start; }

    public LocalDateTime getEnd() { return end; }
    public void setEnd(LocalDateTime end) { this.end = end; }
}