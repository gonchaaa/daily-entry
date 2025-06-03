package com.TaskLogger.logger.service;

import com.TaskLogger.logger.DTOs.DailyEntryResponseDTO;
import com.TaskLogger.logger.DTOs.request.DailyEntryRequestDTO;
import com.TaskLogger.logger.entity.DailyEntry;
import com.TaskLogger.logger.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface IDailyEntryService {
    DailyEntryResponseDTO getDailyEntryById(Long id);
    DailyEntryResponseDTO createDailyEntry(DailyEntryRequestDTO dailyEntryRequestDTO, User user);
    DailyEntryResponseDTO updateDailyEntry(Long id, DailyEntryRequestDTO dailyEntryRequestDTO);
    String summaryEntries(Long id);
    List<DailyEntry> getEntriesForUserBetweenDates(User user, LocalDate start, LocalDate end);
    String summaryEntriesByEmail(String email);
    String summaryEntriesByAuthenticatedUser();

}
