package com.delidrive.delidrive_carsharing.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
