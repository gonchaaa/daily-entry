package com.TaskLogger.logger.controller;

import com.TaskLogger.logger.DTOs.DailyEntryResponseDTO;
import com.TaskLogger.logger.DTOs.UserLoginResponseDTO;
import com.TaskLogger.logger.DTOs.request.DailyEntryRequestDTO;
import com.TaskLogger.logger.entity.User;
import com.TaskLogger.logger.repository.UserRepository;
import com.TaskLogger.logger.service.impl.DailyEntryServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/daily-entry")
public class DailyEntryController {
    private final DailyEntryServiceImpl dailyEntryService;
    private final UserRepository userRepository;

//    @PostMapping("/")
//    public ResponseEntity<DailyEntryResponseDTO> createEntry(@RequestBody DailyEntryRequestDTO dailyEntryRequestDTO,
//                                                             @AuthenticationPrincipal UserDetails userDetails
//                                                                        ,HttpServletResponse response) {
//        String email = userDetails.getUsername();
//        Cookie cookie = new Cookie("dailyEntryId", "true");
//        cookie.setMaxAge(5 * 24 * 60 * 60);
//        cookie.setHttpOnly(true);
//        cookie.setPath("/");
//        response.addCookie(cookie);
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        return ResponseEntity.ok(dailyEntryService.createDailyEntry(dailyEntryRequestDTO,user));
//
//    }
//    @PutMapping("/{id}")
//    public ResponseEntity<DailyEntryResponseDTO> updateEntry(@PathVariable Long id,
//                                                             @RequestBody DailyEntryRequestDTO dailyEntryRequestDTO) {
//        return ResponseEntity.ok(dailyEntryService.updateDailyEntry(id, dailyEntryRequestDTO));
//    }
//
//
//    @GetMapping("/{id}")
//    public ResponseEntity<DailyEntryResponseDTO> getEntryById(@PathVariable Long id) {
//        return ResponseEntity.ok(dailyEntryService.getDailyEntryById(id));
//    }
//
//    @PostMapping("/summary")
//    public ResponseEntity<String> summaryEntries(@AuthenticationPrincipal UserDetails userDetails) {
//        String email = userDetails.getUsername();
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        String summary = dailyEntryService.summaryEntries(user.getId());
//        return ResponseEntity.ok(summary);
//    }


//
//    private final DailyEntryServiceImpl dailyEntryService;
//    private final UserRepository userRepository;

    @GetMapping("/create")
    public String showForm(Model model) {
        model.addAttribute("dailyEntryRequestDTO", new DailyEntryRequestDTO());
        return "daily-entry-form"; // templates/daily-entry-form.html
    }

    @PostMapping("/create")
    public String createEntry(@ModelAttribute DailyEntryRequestDTO dto,
                              HttpSession session,
                              Model model) {
        UserLoginResponseDTO user = (UserLoginResponseDTO) session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/login";
        }

        User dbUser = userRepository.findByEmail(user.getToken())
                .orElseThrow(() -> new RuntimeException("User tapilmadi"));

        dailyEntryService.createDailyEntry(dto, dbUser);

        model.addAttribute("message", "Qeyd uğurla əlavə edildi!");
        return "daily-entry-form"; // eyni form səhifəsində mesaj göstər
    }
}
