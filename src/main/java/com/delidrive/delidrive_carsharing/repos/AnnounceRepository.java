package com.delidrive.delidrive_carsharing.repos;

import com.delidrive.delidrive_carsharing.models.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnounceRepository extends JpaRepository<Announcement, Long> {
}
