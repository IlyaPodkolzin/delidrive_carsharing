package com.delidrive.delidrive_carsharing.services;

import com.delidrive.delidrive_carsharing.models.User;
import com.delidrive.delidrive_carsharing.models.enums.Role;
import com.delidrive.delidrive_carsharing.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null)
            return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        log.info("Saving user with email: {}", user.getEmail());
        userRepository.save(user);
        return true;
    }
}
