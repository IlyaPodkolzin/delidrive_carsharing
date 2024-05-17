package com.delidrive.delidrive_carsharing.services;

import com.delidrive.delidrive_carsharing.models.Announcement;
import com.delidrive.delidrive_carsharing.models.User;
import com.delidrive.delidrive_carsharing.models.enums.Role;
import com.delidrive.delidrive_carsharing.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

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

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public List<Announcement> getRentedAnnouncements(Principal principal) {
        if (principal == null) return null;
        User user = getUserByPrincipal(principal);
        return user.getTakenAnnouncements();
    }

    public List<Announcement> getAnnouncements(Principal principal) {
        if (principal == null) return null;
        User user = getUserByPrincipal(principal);
        return user.getAnnouncements();
    }

    public void removeUserById(Long id) {
        userRepository.deleteById(id);
    }
}
