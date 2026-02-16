package it.unicam.roombooker.controller;

import it.unicam.roombooker.dto.CreateReservationRequest;
import it.unicam.roombooker.dto.DtoMapper;
import it.unicam.roombooker.dto.ReservationDTO;
import it.unicam.roombooker.model.Reservation;
import it.unicam.roombooker.service.ReservationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Get all reservations - ADMIN only (used by admin panel)
     */
    @GetMapping
    public List<ReservationDTO> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return reservations.stream()
                .map(DtoMapper::toReservationDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crea prenotazione per l'utente loggato (JWT)
     */
    @PostMapping
    public ReservationDTO createReservation(@RequestBody CreateReservationRequest req,
                                            Authentication authentication) {
        String email = authentication.getName();
        Reservation res = reservationService.createReservationForEmail(
                email, req.getRoomId(), req.getStart(), req.getEnd()
        );
        return DtoMapper.toReservationDTO(res);
    }

    /**
     * Prenotazioni dell'utente loggato
     */
    @GetMapping("/me")
    public List<ReservationDTO> myReservations(Authentication authentication) {
        String email = authentication.getName();
        return reservationService.getUserReservationsByEmail(email)
                .stream()
                .map(DtoMapper::toReservationDTO)
                .toList();
    }

    /**
     * Cancella prenotazione:
     * - ADMIN: può cancellare tutto
     * - USER: solo le proprie
     */
    @DeleteMapping("/{id}")
    public ReservationDTO deleteReservation(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_ADMIN"));

        Reservation res = reservationService.cancelReservation(id, email, isAdmin);
        return DtoMapper.toReservationDTO(res);
    }
}