package com.TaskLogger.logger.service.impl;

import com.TaskLogger.logger.DTOs.DailyEntryResponseDTO;
import com.TaskLogger.logger.DTOs.UserLoginResponseDTO;
import com.TaskLogger.logger.DTOs.request.DailyEntryRequestDTO;
import com.TaskLogger.logger.DTOs.request.UserLoginRequestDTO;
import com.TaskLogger.logger.DTOs.request.UserRegisterRequestDTO;
import com.TaskLogger.logger.entity.DailyEntry;
import com.TaskLogger.logger.entity.User;
import com.TaskLogger.logger.enums.Status;
import com.TaskLogger.logger.repository.DailyEntryRepository;
import com.TaskLogger.logger.repository.UserRepository;
import com.TaskLogger.logger.service.IAuthService;
import com.TaskLogger.logger.service.IDailyEntryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyEntryServiceImpl implements IDailyEntryService {

    private final DailyEntryRepository dailyEntryRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public DailyEntryResponseDTO getDailyEntryById(Long id) {

        DailyEntry dailyEntry = dailyEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DailyEntry not found with id: " + id));

        return modelMapper.map(dailyEntry, DailyEntryResponseDTO.class);
    }

    @Override
    public DailyEntryResponseDTO createDailyEntry(DailyEntryRequestDTO dailyEntryRequestDTO, User user) {
        DailyEntryResponseDTO dailyEntryResponseDTO = new DailyEntryResponseDTO();
        DailyEntry dailyEntry = new DailyEntry();
        modelMapper.map(dailyEntryRequestDTO,dailyEntry);
        dailyEntry.setUser(user);
        dailyEntry.setStatus(Status.PENDING);
        DailyEntry savedEntry =  dailyEntryRepository.save(dailyEntry);
        modelMapper.map(savedEntry,dailyEntryResponseDTO);

        return dailyEntryResponseDTO;
    }

    @Override
    public DailyEntryResponseDTO updateDailyEntry(Long id, DailyEntryRequestDTO dailyEntryRequestDTO) {
        DailyEntry dailyEntry = dailyEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DailyEntry not found with id: " + id));
        DailyEntryResponseDTO dailyEntryResponseDTO = new DailyEntryResponseDTO();
        dailyEntry.setStatus(dailyEntryRequestDTO.getStatus());
        dailyEntry.setDescription(dailyEntryRequestDTO.getDescription());
        dailyEntry.setDate(dailyEntryRequestDTO.getDate());
        dailyEntry.setTitle(dailyEntryRequestDTO.getTitle());
        DailyEntry updatedEntry = dailyEntryRepository.save(dailyEntry);
        modelMapper.map(updatedEntry,dailyEntryResponseDTO);

        return dailyEntryResponseDTO;
    }

    @Override
    public String summaryEntries(Long id) {
        LocalDate currentDate = LocalDate.now();
        LocalDate entryDate = LocalDate.now().minusDays(4);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        List<DailyEntry> entries = dailyEntryRepository.findByUserAndDateBetween(user, entryDate, currentDate);

        StringBuilder content = new StringBuilder("Heftelik mail");

        for (DailyEntry entry : entries){
            content.append("Date :").append(entry.getDate()).append("\n");
            content.append("Status :").append(entry.getStatus()).append("\n");
            content.append("Description :").append(entry.getDescription()).append("\n");
            content.append("\n");
        }
    return content.toString();
    }
}
