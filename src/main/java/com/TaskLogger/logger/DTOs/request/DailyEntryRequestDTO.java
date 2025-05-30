package com.TaskLogger.logger.DTOs.request;

import com.TaskLogger.logger.enums.Status;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyEntryRequestDTO {
    private LocalDate date;
    private String title;
    private String description;
    private Status status;
}
