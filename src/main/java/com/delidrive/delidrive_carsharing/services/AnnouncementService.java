package com.delidrive.delidrive_carsharing.services;

import com.delidrive.delidrive_carsharing.models.Announcement;
import com.delidrive.delidrive_carsharing.models.User;
import com.delidrive.delidrive_carsharing.models.enums.Role;
import com.delidrive.delidrive_carsharing.repositories.AnnouncementRepository;
import com.delidrive.delidrive_carsharing.repositories.ImageRepository;
import com.delidrive.delidrive_carsharing.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

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
                                 Principal principal,
                                 MultipartFile file1,
                                 MultipartFile file2,
                                 MultipartFile file3,
                                 MultipartFile file4) throws IOException {
        Announcement announcement = new Announcement(car_name, power, seats, color, generation, full_text, price);
        announcement.setUser(getUserByPrincipal(principal));

        imagesAction(file1, file2, file3, file4, announcement);
        announcementRepository.save(announcement);
    }

    private com.delidrive.delidrive_carsharing.models.Image toImageEntity(MultipartFile file) throws IOException {
        com.delidrive.delidrive_carsharing.models.Image image = new com.delidrive.delidrive_carsharing.models.Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
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
                                 String price,
                                 MultipartFile file1,
                                 MultipartFile file2,
                                 MultipartFile file3,
                                 MultipartFile file4) throws IOException {
        Announcement announcement = getAnnouncementById(id).orElseThrow();
        announcement.setCar_name(car_name);
        announcement.setPower(power);
        announcement.setSeats(seats);
        announcement.setColor(color);
        announcement.setGeneration(generation);
        announcement.setFull_text(full_text);
        announcement.setPrice(price);

        if (file1.getSize() != 0 || file2.getSize() != 0 || file3.getSize() != 0 || file4.getSize() != 0) {
            imageRepository.deleteAllInBatch(announcement.getImages());
            announcement.setImages(new ArrayList<>());
            imagesAction(file1, file2, file3, file4, announcement);
        }
        announcementRepository.save(announcement);
    }

    public void imagesAction(MultipartFile file1, MultipartFile file2,
                             MultipartFile file3, MultipartFile file4, Announcement announcement) throws IOException {
        com.delidrive.delidrive_carsharing.models.Image image1;
        com.delidrive.delidrive_carsharing.models.Image image2;
        com.delidrive.delidrive_carsharing.models.Image image3;
        com.delidrive.delidrive_carsharing.models.Image image4;
        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            announcement.addImageToAnnouncement(image1);
        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            if (file1.getSize() == 0) image2.setPreviewImage(true);
            announcement.addImageToAnnouncement(image2);
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity(file3);
            if (file1.getSize() == 0 && file2.getSize() == 0) image3.setPreviewImage(true);
            announcement.addImageToAnnouncement(image3);
        }
        if (file4.getSize() != 0) {
            image4 = toImageEntity(file4);
            if (file1.getSize() == 0 && file2.getSize() == 0 && file3.getSize() == 0) image4.setPreviewImage(true);
            announcement.addImageToAnnouncement(image4);
        }

        Announcement announcementFromDataBase = announcementRepository.save(announcement);
        if (!announcementFromDataBase.getImages().isEmpty()) {
            announcementFromDataBase.setPreviewImageId(announcementFromDataBase.getImages().getFirst().getId());
        }
        else {
            announcementFromDataBase.setPreviewImageId(1L);
        }
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
        model.addAttribute("images", res.getFirst().getImages());
        model.addAttribute("isAdmin", getUserByPrincipal(principal).getRoles().contains(Role.ROLE_ADMIN));
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
