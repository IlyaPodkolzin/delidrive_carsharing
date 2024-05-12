package com.delidrive.delidrive_carsharing.controllers;


import com.delidrive.delidrive_carsharing.services.AnnouncementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class AnnouncementController {

    private static final Logger log = LoggerFactory.getLogger(AnnouncementController.class);
    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/announcement")
    public String announcementMain(Model model, Principal principal) {
        announcementService.createAnnouncementsList(model, principal);
        return "announcement-main";
    }

    @GetMapping("/announcement/add")
    public String announcementAdd(Model model) {
        return "announcement-add";
    }

    @PostMapping("/announcement/add")
    public String announcementAdd(@RequestParam String car_name,
                                  @RequestParam String power,
                                  @RequestParam String seats,
                                  @RequestParam String color,
                                  @RequestParam String generation,
                                  @RequestParam String full_text,
                                  @RequestParam String price,
                                  Principal principal) {
        announcementService.saveAnnouncement(car_name, power, seats, color, generation, full_text, price, principal);
        return "redirect:/announcement";
    }

    @GetMapping("/announcement/{id}")
    public String announcementInfo(@PathVariable(value = "id") long id, Model model, Principal principal) {
        if (!announcementService.checkIfAnnouncementExists(id)) {
            return "redirect:/announcement";
        }

        announcementService.retrieveAnnouncement(id, model, principal);
        return "announcement-info";
    }

    @GetMapping("/announcement/{id}/edit")
    public String announcementEdit(@PathVariable(value = "id") long id, Model model, Principal principal) {
        if (!announcementService.checkIfAnnouncementExists(id)) {
            return "redirect:/announcement";
        }

        announcementService.retrieveAnnouncement(id, model, principal);
        return "announcement-edit";
    }

    @PostMapping("/announcement/{id}/edit")
    public String announcementUpdate(@PathVariable(value = "id") long id,
                                 @RequestParam String car_name,
                                 @RequestParam String power,
                                 @RequestParam String seats,
                                 @RequestParam String color,
                                 @RequestParam String generation,
                                 @RequestParam String full_text,
                                 @RequestParam String price,
                                 Model model) {
        announcementService.editAnnouncement(id, car_name, power, seats, color, generation, full_text, price);
        return "redirect:/announcement";
    }

    @PostMapping("/announcement/{id}/remove")
    public String announcementDelete(@PathVariable(value = "id") long id) {
        announcementService.deleteAnnouncement(id);
        return "redirect:/announcement";
    }

    @PostMapping("/announcement/{id}/rent")
    public String announcementRent(@PathVariable(value = "id") long id, Principal principal) {
        announcementService.rentCarFromAnnouncement(id, principal);
        return "redirect:/announcement";
    }

    @PostMapping("/announcement/{id}/unrent")
    public String announcementUnrent(@PathVariable(value = "id") long id, Principal principal) {
        announcementService.unrentCarFromAnnouncement(id, principal);
        return "redirect:/announcement";
    }
}
