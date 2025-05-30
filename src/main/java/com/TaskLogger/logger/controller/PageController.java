package com.TaskLogger.logger.controller;

import com.TaskLogger.logger.DTOs.UserLoginResponseDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/index")
    public String indexPage(HttpSession session, Model model) {
        System.out.println("ssssssssss");
        UserLoginResponseDTO user = (UserLoginResponseDTO) session.getAttribute("user");
        System.out.println("user: " + user);
        if (user == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("username", user.getToken());
        return "index";
    }
}

