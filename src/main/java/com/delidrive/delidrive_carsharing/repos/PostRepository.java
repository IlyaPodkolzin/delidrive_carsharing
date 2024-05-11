package com.delidrive.delidrive_carsharing.repos;

import com.delidrive.delidrive_carsharing.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
