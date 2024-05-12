package com.delidrive.delidrive_carsharing.repositories;

import com.delidrive.delidrive_carsharing.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
