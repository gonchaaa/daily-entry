package com.TaskLogger.logger.DTOs.request;

import com.TaskLogger.logger.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyEntryRequestDTO {
    private LocalDate date;
    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Status status;
}
