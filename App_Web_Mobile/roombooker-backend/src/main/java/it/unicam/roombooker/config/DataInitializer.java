package it.unicam.roombooker.config;

import it.unicam.roombooker.model.Role;
import it.unicam.roombooker.model.Room;
import it.unicam.roombooker.model.User;
import it.unicam.roombooker.repository.RoomRepository;
import it.unicam.roombooker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           RoomRepository roomRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        upsertUser("admin@demo.it", "Admin123!", Role.ADMIN);
        upsertUser("user@demo.it", "User123!", Role.USER);

        if (roomRepository.count() == 0) {
            Room r1 = new Room();
            r1.setName("Aula 1");
            r1.setBuilding("Blocco A");
            r1.setCapacity(30);

            Room r2 = new Room();
            r2.setName("Aula 2");
            r2.setBuilding("Blocco B");
            r2.setCapacity(20);

            roomRepository.save(r1);
            roomRepository.save(r2);
        }

        System.out.println("✅ Demo data ready. Use admin@demo.it / Admin123!  and user@demo.it / User123!");
    }

    private void upsertUser(String email, String rawPassword, Role role) {
        User u = userRepository.findByEmail(email).orElseGet(User::new);
        u.setEmail(email);
        u.setRole(role);

        // Se l'utente esiste già ma aveva una password diversa, la riallineo sempre.
        // (così le credenziali demo sono STABILI ad ogni avvio)
        u.setPasswordHash(passwordEncoder.encode(rawPassword));

        userRepository.save(u);
    }
}