package pl.sda.auctions.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.auctions.model.Role;
import pl.sda.auctions.model.User;
import pl.sda.auctions.model.dto.ChangePasswordForm;
import pl.sda.auctions.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean checkIfUserExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean checkIfMailIsTaken(String email) {
        return userRepository.checkIfMailExists(email);
    }

    public boolean checkIfNameIsTaken(String email) {
        return userRepository.checkIfNameExists(email);
    }

    public void createUser(String name, String email, String password, Role role) {
        var user = new User(
                null,
                email,
                passwordEncoder.encode(password),
                name,
                true,
                role

        );
        userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean changePassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("User with " + email + " doesn't exist."));
        if (user.getPassword().equals(passwordEncoder.encode(oldPassword))) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

}

