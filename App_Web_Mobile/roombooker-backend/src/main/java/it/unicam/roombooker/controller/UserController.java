package it.unicam.roombooker.controller;

import it.unicam.roombooker.dto.DtoMapper;
import it.unicam.roombooker.dto.UserDTO;
import it.unicam.roombooker.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(DtoMapper::toUserDTO)
                .toList();
    }
}