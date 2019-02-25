package com.incentro.ypa.devops.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/hello")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }
    @GetMapping("/liedje")
    public String song(@RequestParam(name = "songName", required = true, defaultValue = "") String songName, Model model) {
        model.addAttribute("songName", songName);
        return "song";
    }

}