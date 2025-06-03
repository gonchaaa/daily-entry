package com.TaskLogger.logger.controller;

import com.TaskLogger.logger.DTOs.request.DailyEntryRequestDTO;
import com.TaskLogger.logger.entity.User;
import com.TaskLogger.logger.repository.UserRepository;
import com.TaskLogger.logger.service.IDailyEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DailyMailyController {

    private final IDailyEntryService dailyEntryService;
    private final UserRepository userRepository;

    @GetMapping("/daily")
    public String showDailyPAge(){
        return "daily";
    }

    @PostMapping("/daily/save")
    @ResponseBody
    public String saveEntry(@RequestBody DailyEntryRequestDTO request,
                            @AuthenticationPrincipal UserDetails userDetails){
        User user = new User();
        user.setEmail(userDetails.getUsername());
        dailyEntryService.createDailyEntry(request, user);
        return "Saved";
    }

    @GetMapping("/daily/summary")
    @ResponseBody
    public String getSummary(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("Fetching summary for user: " + userDetails.getUsername());
        return dailyEntryService.summaryEntriesByAuthenticatedUser();
    }

    @PostMapping("/daily/summary")
    public ResponseEntity<String> receiveEntriesAndSummarize(@RequestBody List<DailyEntryRequestDTO> entriesDto,
                                                             @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        for (DailyEntryRequestDTO dto : entriesDto) {
            dailyEntryService.createDailyEntry(dto, user);
        }

        String summary = dailyEntryService.summaryEntries(user.getId());

        return ResponseEntity.ok(summary);
    }




}
