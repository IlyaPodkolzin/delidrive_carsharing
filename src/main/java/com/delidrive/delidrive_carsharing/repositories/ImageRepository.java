package com.delidrive.delidrive_carsharing.repositories;

import com.delidrive.delidrive_carsharing.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, Long> {
}
