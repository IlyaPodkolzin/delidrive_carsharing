package com.delidrive.delidrive_carsharing.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String car_name, power, seats, color, generation, full_text, price;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User renter;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "announcement")
    private List<Image> images = new ArrayList<>();

    private Long previewImageId;

    private LocalDate dateOfCreated;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDate.now();
    }

    public void addImageToAnnouncement(Image image) {
        image.setAnnouncement(this);
        images.add(image);
    }

    public Announcement() {
    }

    public Announcement(String car_name, String power, String seats, String color, String generation, String full_text, String price) {
        this.car_name = car_name;
        this.power = power;
        this.seats = seats;
        this.color = color;
        this.generation = generation;
        this.full_text = full_text;
        this.price = price;
    }

}
