package com.delidrive.delidrive_carsharing.services;

import com.delidrive.delidrive_carsharing.models.Announcement;
import com.delidrive.delidrive_carsharing.models.User;
import com.delidrive.delidrive_carsharing.repositories.AnnouncementRepository;
import com.delidrive.delidrive_carsharing.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository;

    public void createAnnouncementsList(Model model, Principal principal) {
        model.addAttribute("announcements", announcementRepository.findAll());
        model.addAttribute("users", getUserByPrincipal(principal));
    }

    public boolean checkIfAnnouncementExists(long id) {
        return announcementRepository.existsById(id);
    }

    public void saveAnnouncement(String car_name,
                                 String power,
                                 String seats,
                                 String color,
                                 String generation,
                                 String full_text,
                                 String price,
                                 Principal principal) {
        Announcement announcement = new Announcement(car_name, power, seats, color, generation, full_text, price);
        announcement.setUser(getUserByPrincipal(principal));
        announcementRepository.save(announcement);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public void editAnnouncement(long id,
                                 String car_name,
                                 String power,
                                 String seats,
                                 String color,
                                 String generation,
                                 String full_text,
                                 String price) {
        Announcement announcement = getAnnouncementById(id).orElseThrow();
        announcement.setCar_name(car_name);
        announcement.setPower(power);
        announcement.setSeats(seats);
        announcement.setColor(color);
        announcement.setGeneration(generation);
        announcement.setFull_text(full_text);
        announcement.setPrice(price);
        announcementRepository.save(announcement);
    }

    public void deleteAnnouncement(long id) {
        Announcement announcement = getAnnouncementById(id).orElseThrow();
        announcementRepository.delete(announcement);
    }

    public Optional<Announcement> getAnnouncementById(long id) {
        return announcementRepository.findById(id);
    }

    public void retrieveAnnouncement(long id, Model model, Principal principal) {
        Optional<Announcement> announcement = announcementRepository.findById(id);
        ArrayList<Announcement> res = new ArrayList<>();
        announcement.ifPresent(res::add);
        model.addAttribute("announcement", res);
        model.addAttribute("currentUser", getUserByPrincipal(principal));
    }

    public void rentCarFromAnnouncement(long id, Principal principal) {
        Announcement announcement = getAnnouncementById(id).orElseThrow();
        announcement.setRenter(getUserByPrincipal(principal));
        announcementRepository.save(announcement);
    }

    public void unrentCarFromAnnouncement(long id, Principal principal) {
        Announcement announcement = getAnnouncementById(id).orElseThrow();
        announcement.setRenter(null);
        announcementRepository.save(announcement);
    }
}
