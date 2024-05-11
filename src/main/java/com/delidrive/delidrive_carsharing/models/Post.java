package com.delidrive.delidrive_carsharing.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title, announce, full_text;
    private int views;

    public Post(String title, String announce, String full_text) {
        this.title = title;
        this.announce = announce;
        this.full_text = full_text;
    }

    public Post() {

    }

}
