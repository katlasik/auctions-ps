package pl.sda.auctions.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.auctions.dto.UserDto;
import pl.sda.auctions.model.Role;
import pl.sda.auctions.model.User;
import pl.sda.auctions.repository.UserRepository;

@Service
public class ChangePasswordService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public ChangePasswordService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    public UserDto changePassword(String email, String newPassword){
        User user = userRepository.findByEmail(email)
                .map(u -> {
                        u.setPassword(passwordEncoder.encode(newPassword));
                        return userRepository.save(u);}).orElseThrow();
        return new UserDto();
    }
}
