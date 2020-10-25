package pl.sda.auctions.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.auctions.model.Role;
import pl.sda.auctions.model.User;
import pl.sda.auctions.repository.UserRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
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
        logger.info("User was created. User = {}", user);
        userRepository.save(user);


        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo("krzysztofatlasik@gmail.com");
            helper.setReplyTo("newsletter@codecouple.pl");
            helper.setFrom("newsletter@codecouple.pl");
            helper.setSubject("hello");
            helper.setText("Hello darkness", true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mail);

    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
