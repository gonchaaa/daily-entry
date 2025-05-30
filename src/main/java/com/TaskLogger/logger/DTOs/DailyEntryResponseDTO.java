package com.TaskLogger.logger.DTOs;

import com.TaskLogger.logger.enums.Status;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyEntryResponseDTO {
    private LocalDate date;
    private String title;
    private String description;
    private Status status;
}
