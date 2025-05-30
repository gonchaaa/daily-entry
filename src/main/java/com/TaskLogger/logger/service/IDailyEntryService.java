package com.TaskLogger.logger.service;

import com.TaskLogger.logger.DTOs.DailyEntryResponseDTO;
import com.TaskLogger.logger.DTOs.request.DailyEntryRequestDTO;
import com.TaskLogger.logger.entity.User;

public interface IDailyEntryService {
    DailyEntryResponseDTO getDailyEntryById(Long id);
    DailyEntryResponseDTO createDailyEntry(DailyEntryRequestDTO dailyEntryRequestDTO, User user);
    DailyEntryResponseDTO updateDailyEntry(Long id, DailyEntryRequestDTO dailyEntryRequestDTO);
    String summaryEntries(Long id);
}
