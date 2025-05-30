package com.TaskLogger.logger.controller;


import com.TaskLogger.logger.DTOs.UserLoginResponseDTO;
import com.TaskLogger.logger.DTOs.request.UserLoginRequestDTO;
import com.TaskLogger.logger.DTOs.request.UserRegisterRequestDTO;
import com.TaskLogger.logger.service.impl.AuthServiceImpl;
import com.TaskLogger.logger.service.impl.JwtService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthServiceImpl authService;
    private final JwtService jwtService;
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserRegisterRequestDTO());
        return "register"; // templates/register.html
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegisterRequestDTO userRegisterRequestDTO,Model model) {
        try{
            authService.registerUser(userRegisterRequestDTO);
            System.out.println("register ugurludu");
            return "redirect:/auth/login";

        }
        catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserLoginRequestDTO());
        System.out.println("logine kecildi");
        return "login"; // templates/login.html
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<UserLoginResponseDTO> loginUser(@RequestBody UserLoginRequestDTO loginDTO,
                                                          HttpSession session) {
        try {
            System.out.println(loginDTO.toString());
            UserLoginResponseDTO response = authService.loginUser(loginDTO);
            session.setAttribute("user", response);
            System.out.println("login ugurlu ola biler");
            return ResponseEntity.ok(response); // token burada gələcək
        } catch (Exception e) {
            System.out.println("register ugursuzdur " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

//    @PostMapping("/login")
//    public String loginUser(@ModelAttribute("user")  UserLoginRequestDTO userLoginRequestDTO,
//                                                          HttpSession session,
//                                                          Model model) {
//        try {
//            System.out.println("Gələn username: " + userLoginRequestDTO.get());
//            System.out.println("Gələn password: " + userLoginRequestDTO.getPassword());
//            UserLoginResponseDTO response = authService.loginUser(userLoginRequestDTO);
//            session.setAttribute("user", response);
//            return "redirect:/index";
//        } catch (Exception e) {
//            model.addAttribute("error", "Yanlış istifadəçi adı və ya şifrə");
//            return "login";
//        }
//    }
@GetMapping("/me")
public ResponseEntity<?> getMe(@RequestHeader("Authorization") String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token yoxdur");
    }

    String token = authHeader.substring(7);
    String username = jwtService.findUsername(token);
    if (username == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token etibarsızdır");
    }

    Map<String, String> response = new HashMap<>();
    response.put("username", username);
    return ResponseEntity.ok(response);
}
}
