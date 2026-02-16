package it.unicam.roombooker.controller;

import it.unicam.roombooker.dto.DtoMapper;
import it.unicam.roombooker.dto.RoomDTO;
import it.unicam.roombooker.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<RoomDTO> getAllRooms() {
        return roomService.getAllRooms()
                .stream()
                .map(DtoMapper::toRoomDTO)
                .toList();
    }

    @GetMapping("/available")
    public List<RoomDTO> getAvailableRooms(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end,
            @RequestParam(required = false) Integer minCapacity
    ) {
        return roomService.getAvailableRooms(start, end, minCapacity)
                .stream()
                .map(DtoMapper::toRoomDTO)
                .toList();
    }
}