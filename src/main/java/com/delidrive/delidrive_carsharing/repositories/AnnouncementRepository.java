package com.delidrive.delidrive_carsharing.repositories;

import com.delidrive.delidrive_carsharing.models.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
