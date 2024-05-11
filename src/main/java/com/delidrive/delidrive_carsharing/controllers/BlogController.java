package com.delidrive.delidrive_carsharing.controllers;

import com.delidrive.delidrive_carsharing.models.Announcement;
import com.delidrive.delidrive_carsharing.repos.AnnounceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private AnnounceRepository announceRepository;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Announcement> announces = announceRepository.findAll();
        model.addAttribute("announces", announces);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add.html";
    }

    @PostMapping("/blog/add")
    public String blogAddPost(@RequestParam String car_name,
                              @RequestParam String power,
                              @RequestParam String seats,
                              @RequestParam String color,
                              @RequestParam String generation,
                              @RequestParam String full_text,
                              @RequestParam String price,
                              Model model) {
        Announcement announcement = new Announcement(car_name, power, seats, color, generation, full_text, price);
        announceRepository.save(announcement);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogInfo(@PathVariable(value = "id") long id, Model model) {
        if (!announceRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Optional<Announcement> announcement = announceRepository.findById(id);
        ArrayList<Announcement> res = new ArrayList<>();
        announcement.ifPresent(res::add);
        model.addAttribute("announcement", res);
        return "blog-info";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (!announceRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Optional<Announcement> announcement = announceRepository.findById(id);
        ArrayList<Announcement> res = new ArrayList<>();
        announcement.ifPresent(res::add);
        model.addAttribute("announcement", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id,
                                 @RequestParam String car_name,
                                 @RequestParam String power,
                                 @RequestParam String seats,
                                 @RequestParam String color,
                                 @RequestParam String generation,
                                 @RequestParam String full_text,
                                 @RequestParam String price,
                                 Model model) {
        Announcement announcement = announceRepository.findById(id).orElseThrow();
        announcement.setCar_name(car_name);
        announcement.setPower(power);
        announcement.setSeats(seats);
        announcement.setColor(color);
        announcement.setGeneration(generation);
        announcement.setFull_text(full_text);
        announcement.setPrice(price);
        announceRepository.save(announcement);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
        Announcement announcement = announceRepository.findById(id).orElseThrow();
        announceRepository.delete(announcement);
        return "redirect:/blog";
    }
}
