package com.cg.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @GetMapping("/")
    public String showIndex() {
        return "redirect:/products";
    }

    @GetMapping("/login")
    public ModelAndView getLogin() {
        return new ModelAndView("/login");
    }
}
